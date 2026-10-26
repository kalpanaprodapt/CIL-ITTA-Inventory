package com.uniti.cil.itta.inventory.change.service;

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
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.mapper.WorkOrderMapper;
import com.uniti.cil.itta.inventory.change.model.WorkOrder;
import com.uniti.cil.itta.inventory.change.repository.WorkOrderRepository;
import com.uniti.cil.itta.inventory.change.support.WorkOrderFixtures;
import com.uniti.cil.itta.inventory.change.validation.WorkOrderValidator;
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
class WorkOrderServiceImplTest {

    @Mock
    private WorkOrderRepository repository;
    @Mock
    private EventPublisher events;

    private WorkOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(WorkOrderFixtures.TENANT, "tester");
        service = new WorkOrderServiceImpl(repository, new WorkOrderMapper(), new WorkOrderValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        WorkOrder entity = WorkOrderFixtures.entity();
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(WorkOrderFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(WorkOrderFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(WorkOrderFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        WorkOrderCreateRequest request = WorkOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WorkOrderFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(WorkOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        WorkOrderResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<WorkOrder> captor = ArgumentCaptor.forClass(WorkOrder.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(WorkOrderFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        WorkOrderCreateRequest request = WorkOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WorkOrderFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        WorkOrder entity = WorkOrderFixtures.entity();
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(WorkOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        WorkOrderUpdateRequest request = new WorkOrderUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        WorkOrderResponse response = service.update(WorkOrderFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        WorkOrder entity = WorkOrderFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.of(entity));
        WorkOrderUpdateRequest request = new WorkOrderUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(WorkOrderFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        WorkOrder entity = WorkOrderFixtures.entity();
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(WorkOrderFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        WorkOrder entity = WorkOrderFixtures.entity();
        when(repository.findByTenantIdAndId(WorkOrderFixtures.TENANT, WorkOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(WorkOrderFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(WorkOrderFixtures.entity())));
        assertThat(service.search(WorkOrderFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        WorkOrderCreateRequest ok = WorkOrderFixtures.createRequest();
        WorkOrderCreateRequest dup = WorkOrderFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(WorkOrderFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(WorkOrderFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(WorkOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<WorkOrderResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(WorkOrderFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(WorkOrderFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(WorkOrderFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
