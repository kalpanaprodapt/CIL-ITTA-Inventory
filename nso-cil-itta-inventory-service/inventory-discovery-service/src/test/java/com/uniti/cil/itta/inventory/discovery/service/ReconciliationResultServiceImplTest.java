package com.uniti.cil.itta.inventory.discovery.service;

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
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.mapper.ReconciliationResultMapper;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationResult;
import com.uniti.cil.itta.inventory.discovery.repository.ReconciliationResultRepository;
import com.uniti.cil.itta.inventory.discovery.support.ReconciliationResultFixtures;
import com.uniti.cil.itta.inventory.discovery.validation.ReconciliationResultValidator;
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
class ReconciliationResultServiceImplTest {

    @Mock
    private ReconciliationResultRepository repository;
    @Mock
    private EventPublisher events;

    private ReconciliationResultServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ReconciliationResultFixtures.TENANT, "tester");
        service = new ReconciliationResultServiceImpl(repository, new ReconciliationResultMapper(), new ReconciliationResultValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ReconciliationResultFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ReconciliationResultFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ReconciliationResultFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ReconciliationResultCreateRequest request = ReconciliationResultFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReconciliationResultFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ReconciliationResult.class))).thenAnswer(inv -> inv.getArgument(0));
        ReconciliationResultResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ReconciliationResult> captor = ArgumentCaptor.forClass(ReconciliationResult.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ReconciliationResultFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ReconciliationResultCreateRequest request = ReconciliationResultFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReconciliationResultFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ReconciliationResult.class))).thenAnswer(inv -> inv.getArgument(0));
        ReconciliationResultUpdateRequest request = new ReconciliationResultUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ReconciliationResultResponse response = service.update(ReconciliationResultFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.of(entity));
        ReconciliationResultUpdateRequest request = new ReconciliationResultUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ReconciliationResultFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ReconciliationResultFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationResultFixtures.TENANT, ReconciliationResultFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ReconciliationResultFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ReconciliationResultFixtures.entity())));
        assertThat(service.search(ReconciliationResultFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ReconciliationResultCreateRequest ok = ReconciliationResultFixtures.createRequest();
        ReconciliationResultCreateRequest dup = ReconciliationResultFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ReconciliationResultFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ReconciliationResultFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ReconciliationResult.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ReconciliationResultResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ReconciliationResultFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ReconciliationResultFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ReconciliationResultFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
