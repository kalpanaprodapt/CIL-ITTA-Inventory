package com.uniti.cil.itta.inventory.cabling.service;

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
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.mapper.PatchPortMapper;
import com.uniti.cil.itta.inventory.cabling.model.PatchPort;
import com.uniti.cil.itta.inventory.cabling.repository.PatchPortRepository;
import com.uniti.cil.itta.inventory.cabling.support.PatchPortFixtures;
import com.uniti.cil.itta.inventory.cabling.validation.PatchPortValidator;
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
class PatchPortServiceImplTest {

    @Mock
    private PatchPortRepository repository;
    @Mock
    private EventPublisher events;

    private PatchPortServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PatchPortFixtures.TENANT, "tester");
        service = new PatchPortServiceImpl(repository, new PatchPortMapper(), new PatchPortValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PatchPort entity = PatchPortFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PatchPortFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PatchPortFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PatchPortFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PatchPortCreateRequest request = PatchPortFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchPortFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PatchPort.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchPortResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PatchPort> captor = ArgumentCaptor.forClass(PatchPort.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PatchPortFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PatchPortCreateRequest request = PatchPortFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchPortFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PatchPort entity = PatchPortFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PatchPort.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchPortUpdateRequest request = new PatchPortUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PatchPortResponse response = service.update(PatchPortFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PatchPort entity = PatchPortFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.of(entity));
        PatchPortUpdateRequest request = new PatchPortUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PatchPortFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PatchPort entity = PatchPortFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PatchPortFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PatchPort entity = PatchPortFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPortFixtures.TENANT, PatchPortFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PatchPortFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PatchPortFixtures.entity())));
        assertThat(service.search(PatchPortFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PatchPortCreateRequest ok = PatchPortFixtures.createRequest();
        PatchPortCreateRequest dup = PatchPortFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PatchPortFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PatchPortFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PatchPort.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PatchPortResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PatchPortFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PatchPortFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PatchPortFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
