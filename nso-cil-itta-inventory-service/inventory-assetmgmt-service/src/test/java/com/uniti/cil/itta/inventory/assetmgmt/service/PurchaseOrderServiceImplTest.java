package com.uniti.cil.itta.inventory.assetmgmt.service;

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
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.PurchaseOrderMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.PurchaseOrder;
import com.uniti.cil.itta.inventory.assetmgmt.repository.PurchaseOrderRepository;
import com.uniti.cil.itta.inventory.assetmgmt.support.PurchaseOrderFixtures;
import com.uniti.cil.itta.inventory.assetmgmt.validation.PurchaseOrderValidator;
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
class PurchaseOrderServiceImplTest {

    @Mock
    private PurchaseOrderRepository repository;
    @Mock
    private EventPublisher events;

    private PurchaseOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PurchaseOrderFixtures.TENANT, "tester");
        service = new PurchaseOrderServiceImpl(repository, new PurchaseOrderMapper(), new PurchaseOrderValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PurchaseOrderFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PurchaseOrderFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PurchaseOrderFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PurchaseOrderCreateRequest request = PurchaseOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PurchaseOrderFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PurchaseOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        PurchaseOrderResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PurchaseOrder> captor = ArgumentCaptor.forClass(PurchaseOrder.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PurchaseOrderFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PurchaseOrderCreateRequest request = PurchaseOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PurchaseOrderFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PurchaseOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PurchaseOrderResponse response = service.update(PurchaseOrderFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.of(entity));
        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PurchaseOrderFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PurchaseOrderFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        when(repository.findByTenantIdAndId(PurchaseOrderFixtures.TENANT, PurchaseOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PurchaseOrderFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PurchaseOrderFixtures.entity())));
        assertThat(service.search(PurchaseOrderFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PurchaseOrderCreateRequest ok = PurchaseOrderFixtures.createRequest();
        PurchaseOrderCreateRequest dup = PurchaseOrderFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PurchaseOrderFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PurchaseOrderFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PurchaseOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PurchaseOrderResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PurchaseOrderFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PurchaseOrderFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PurchaseOrderFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
