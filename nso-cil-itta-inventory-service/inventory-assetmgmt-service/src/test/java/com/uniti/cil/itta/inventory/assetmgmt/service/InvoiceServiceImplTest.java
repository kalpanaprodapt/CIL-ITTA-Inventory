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
import com.uniti.cil.itta.inventory.assetmgmt.mapper.InvoiceMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.Invoice;
import com.uniti.cil.itta.inventory.assetmgmt.repository.InvoiceRepository;
import com.uniti.cil.itta.inventory.assetmgmt.support.InvoiceFixtures;
import com.uniti.cil.itta.inventory.assetmgmt.validation.InvoiceValidator;
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
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository repository;
    @Mock
    private EventPublisher events;

    private InvoiceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(InvoiceFixtures.TENANT, "tester");
        service = new InvoiceServiceImpl(repository, new InvoiceMapper(), new InvoiceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Invoice entity = InvoiceFixtures.entity();
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(InvoiceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(InvoiceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(InvoiceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        InvoiceCreateRequest request = InvoiceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(InvoiceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Invoice.class))).thenAnswer(inv -> inv.getArgument(0));
        InvoiceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(InvoiceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        InvoiceCreateRequest request = InvoiceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(InvoiceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Invoice entity = InvoiceFixtures.entity();
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Invoice.class))).thenAnswer(inv -> inv.getArgument(0));
        InvoiceUpdateRequest request = new InvoiceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        InvoiceResponse response = service.update(InvoiceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Invoice entity = InvoiceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.of(entity));
        InvoiceUpdateRequest request = new InvoiceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(InvoiceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Invoice entity = InvoiceFixtures.entity();
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(InvoiceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Invoice entity = InvoiceFixtures.entity();
        when(repository.findByTenantIdAndId(InvoiceFixtures.TENANT, InvoiceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(InvoiceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(InvoiceFixtures.entity())));
        assertThat(service.search(InvoiceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        InvoiceCreateRequest ok = InvoiceFixtures.createRequest();
        InvoiceCreateRequest dup = InvoiceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(InvoiceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(InvoiceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Invoice.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<InvoiceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(InvoiceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(InvoiceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(InvoiceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
