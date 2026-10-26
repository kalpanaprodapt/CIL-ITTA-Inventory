package com.uniti.cil.itta.inventory.port.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.mapper.PortMapper;
import com.uniti.cil.itta.inventory.port.model.Port;
import com.uniti.cil.itta.inventory.port.repository.PortRepository;
import com.uniti.cil.itta.inventory.port.support.PortFixtures;
import com.uniti.cil.itta.inventory.port.validation.PortValidator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class PortServiceImplTest {

    @Mock
    private PortRepository repository;
    @Mock
    private EventPublisher events;

    private PortServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PortFixtures.TENANT, "tester");
        service = new PortServiceImpl(repository, new PortMapper(), new PortValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Port entity = PortFixtures.entity();
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PortFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PortFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PortFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PortCreateRequest request = PortFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PortFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Port.class))).thenAnswer(inv -> inv.getArgument(0));
        PortResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Port> captor = ArgumentCaptor.forClass(Port.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PortFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PortCreateRequest request = PortFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PortFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Port entity = PortFixtures.entity();
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Port.class))).thenAnswer(inv -> inv.getArgument(0));
        PortUpdateRequest request = new PortUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PortResponse response = service.update(PortFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Port entity = PortFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.of(entity));
        PortUpdateRequest request = new PortUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PortFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Port entity = PortFixtures.entity();
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PortFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Port entity = PortFixtures.entity();
        when(repository.findByTenantIdAndId(PortFixtures.TENANT, PortFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PortFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PortFixtures.entity())));
        assertThat(service.search(PortFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PortCreateRequest ok = PortFixtures.createRequest();
        PortCreateRequest dup = PortFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PortFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PortFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Port.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PortResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PortFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PortFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PortFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
