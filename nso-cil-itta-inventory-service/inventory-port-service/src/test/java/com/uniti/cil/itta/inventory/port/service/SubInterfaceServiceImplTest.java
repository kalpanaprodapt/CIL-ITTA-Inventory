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
import com.uniti.cil.itta.inventory.port.mapper.SubInterfaceMapper;
import com.uniti.cil.itta.inventory.port.model.SubInterface;
import com.uniti.cil.itta.inventory.port.repository.SubInterfaceRepository;
import com.uniti.cil.itta.inventory.port.support.SubInterfaceFixtures;
import com.uniti.cil.itta.inventory.port.validation.SubInterfaceValidator;
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
class SubInterfaceServiceImplTest {

    @Mock
    private SubInterfaceRepository repository;
    @Mock
    private EventPublisher events;

    private SubInterfaceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SubInterfaceFixtures.TENANT, "tester");
        service = new SubInterfaceServiceImpl(repository, new SubInterfaceMapper(), new SubInterfaceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SubInterface entity = SubInterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SubInterfaceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SubInterfaceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SubInterfaceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SubInterfaceCreateRequest request = SubInterfaceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SubInterfaceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SubInterface.class))).thenAnswer(inv -> inv.getArgument(0));
        SubInterfaceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SubInterface> captor = ArgumentCaptor.forClass(SubInterface.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SubInterfaceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SubInterfaceCreateRequest request = SubInterfaceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SubInterfaceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SubInterface entity = SubInterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SubInterface.class))).thenAnswer(inv -> inv.getArgument(0));
        SubInterfaceUpdateRequest request = new SubInterfaceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SubInterfaceResponse response = service.update(SubInterfaceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SubInterface entity = SubInterfaceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        SubInterfaceUpdateRequest request = new SubInterfaceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SubInterfaceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SubInterface entity = SubInterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SubInterfaceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SubInterface entity = SubInterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(SubInterfaceFixtures.TENANT, SubInterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SubInterfaceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SubInterfaceFixtures.entity())));
        assertThat(service.search(SubInterfaceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SubInterfaceCreateRequest ok = SubInterfaceFixtures.createRequest();
        SubInterfaceCreateRequest dup = SubInterfaceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SubInterfaceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SubInterfaceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SubInterface.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SubInterfaceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SubInterfaceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SubInterfaceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SubInterfaceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
