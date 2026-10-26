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
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveredAssetMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveredAsset;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveredAssetRepository;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveredAssetFixtures;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveredAssetValidator;
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
class DiscoveredAssetServiceImplTest {

    @Mock
    private DiscoveredAssetRepository repository;
    @Mock
    private EventPublisher events;

    private DiscoveredAssetServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(DiscoveredAssetFixtures.TENANT, "tester");
        service = new DiscoveredAssetServiceImpl(repository, new DiscoveredAssetMapper(), new DiscoveredAssetValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(DiscoveredAssetFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(DiscoveredAssetFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(DiscoveredAssetFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        DiscoveredAssetCreateRequest request = DiscoveredAssetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveredAssetFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(DiscoveredAsset.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveredAssetResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<DiscoveredAsset> captor = ArgumentCaptor.forClass(DiscoveredAsset.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(DiscoveredAssetFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        DiscoveredAssetCreateRequest request = DiscoveredAssetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveredAssetFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(DiscoveredAsset.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveredAssetUpdateRequest request = new DiscoveredAssetUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        DiscoveredAssetResponse response = service.update(DiscoveredAssetFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.of(entity));
        DiscoveredAssetUpdateRequest request = new DiscoveredAssetUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(DiscoveredAssetFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(DiscoveredAssetFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveredAssetFixtures.TENANT, DiscoveredAssetFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(DiscoveredAssetFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(DiscoveredAssetFixtures.entity())));
        assertThat(service.search(DiscoveredAssetFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        DiscoveredAssetCreateRequest ok = DiscoveredAssetFixtures.createRequest();
        DiscoveredAssetCreateRequest dup = DiscoveredAssetFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(DiscoveredAssetFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(DiscoveredAssetFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(DiscoveredAsset.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<DiscoveredAssetResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(DiscoveredAssetFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(DiscoveredAssetFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(DiscoveredAssetFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
