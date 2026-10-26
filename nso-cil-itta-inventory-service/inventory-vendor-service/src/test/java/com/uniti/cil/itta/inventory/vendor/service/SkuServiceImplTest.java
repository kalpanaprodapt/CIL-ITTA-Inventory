package com.uniti.cil.itta.inventory.vendor.service;

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
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.mapper.SkuMapper;
import com.uniti.cil.itta.inventory.vendor.model.Sku;
import com.uniti.cil.itta.inventory.vendor.repository.SkuRepository;
import com.uniti.cil.itta.inventory.vendor.support.SkuFixtures;
import com.uniti.cil.itta.inventory.vendor.validation.SkuValidator;
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
class SkuServiceImplTest {

    @Mock
    private SkuRepository repository;
    @Mock
    private EventPublisher events;

    private SkuServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SkuFixtures.TENANT, "tester");
        service = new SkuServiceImpl(repository, new SkuMapper(), new SkuValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Sku entity = SkuFixtures.entity();
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SkuFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SkuFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SkuFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SkuCreateRequest request = SkuFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SkuFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Sku.class))).thenAnswer(inv -> inv.getArgument(0));
        SkuResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Sku> captor = ArgumentCaptor.forClass(Sku.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SkuFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SkuCreateRequest request = SkuFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SkuFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Sku entity = SkuFixtures.entity();
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Sku.class))).thenAnswer(inv -> inv.getArgument(0));
        SkuUpdateRequest request = new SkuUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SkuResponse response = service.update(SkuFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Sku entity = SkuFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.of(entity));
        SkuUpdateRequest request = new SkuUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SkuFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Sku entity = SkuFixtures.entity();
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SkuFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Sku entity = SkuFixtures.entity();
        when(repository.findByTenantIdAndId(SkuFixtures.TENANT, SkuFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SkuFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SkuFixtures.entity())));
        assertThat(service.search(SkuFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SkuCreateRequest ok = SkuFixtures.createRequest();
        SkuCreateRequest dup = SkuFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SkuFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SkuFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Sku.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SkuResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SkuFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SkuFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SkuFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
