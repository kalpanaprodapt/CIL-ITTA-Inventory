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
import com.uniti.cil.itta.inventory.assetmgmt.mapper.AssetMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.Asset;
import com.uniti.cil.itta.inventory.assetmgmt.repository.AssetRepository;
import com.uniti.cil.itta.inventory.assetmgmt.support.AssetFixtures;
import com.uniti.cil.itta.inventory.assetmgmt.validation.AssetValidator;
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
class AssetServiceImplTest {

    @Mock
    private AssetRepository repository;
    @Mock
    private EventPublisher events;

    private AssetServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(AssetFixtures.TENANT, "tester");
        service = new AssetServiceImpl(repository, new AssetMapper(), new AssetValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Asset entity = AssetFixtures.entity();
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(AssetFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(AssetFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(AssetFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        AssetCreateRequest request = AssetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AssetFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Asset.class))).thenAnswer(inv -> inv.getArgument(0));
        AssetResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Asset> captor = ArgumentCaptor.forClass(Asset.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(AssetFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        AssetCreateRequest request = AssetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AssetFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Asset entity = AssetFixtures.entity();
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Asset.class))).thenAnswer(inv -> inv.getArgument(0));
        AssetUpdateRequest request = new AssetUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        AssetResponse response = service.update(AssetFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Asset entity = AssetFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.of(entity));
        AssetUpdateRequest request = new AssetUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(AssetFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Asset entity = AssetFixtures.entity();
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(AssetFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Asset entity = AssetFixtures.entity();
        when(repository.findByTenantIdAndId(AssetFixtures.TENANT, AssetFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(AssetFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(AssetFixtures.entity())));
        assertThat(service.search(AssetFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        AssetCreateRequest ok = AssetFixtures.createRequest();
        AssetCreateRequest dup = AssetFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(AssetFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(AssetFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Asset.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<AssetResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(AssetFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AssetFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(AssetFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
