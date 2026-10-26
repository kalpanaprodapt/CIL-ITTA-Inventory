package com.uniti.cil.itta.inventory.virtual.service;

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
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.mapper.K8sClusterMapper;
import com.uniti.cil.itta.inventory.virtual.model.K8sCluster;
import com.uniti.cil.itta.inventory.virtual.repository.K8sClusterRepository;
import com.uniti.cil.itta.inventory.virtual.support.K8sClusterFixtures;
import com.uniti.cil.itta.inventory.virtual.validation.K8sClusterValidator;
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
class K8sClusterServiceImplTest {

    @Mock
    private K8sClusterRepository repository;
    @Mock
    private EventPublisher events;

    private K8sClusterServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(K8sClusterFixtures.TENANT, "tester");
        service = new K8sClusterServiceImpl(repository, new K8sClusterMapper(), new K8sClusterValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        K8sCluster entity = K8sClusterFixtures.entity();
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(K8sClusterFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(K8sClusterFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(K8sClusterFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        K8sClusterCreateRequest request = K8sClusterFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(K8sClusterFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(K8sCluster.class))).thenAnswer(inv -> inv.getArgument(0));
        K8sClusterResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<K8sCluster> captor = ArgumentCaptor.forClass(K8sCluster.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(K8sClusterFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        K8sClusterCreateRequest request = K8sClusterFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(K8sClusterFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        K8sCluster entity = K8sClusterFixtures.entity();
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(K8sCluster.class))).thenAnswer(inv -> inv.getArgument(0));
        K8sClusterUpdateRequest request = new K8sClusterUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        K8sClusterResponse response = service.update(K8sClusterFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        K8sCluster entity = K8sClusterFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.of(entity));
        K8sClusterUpdateRequest request = new K8sClusterUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(K8sClusterFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        K8sCluster entity = K8sClusterFixtures.entity();
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(K8sClusterFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        K8sCluster entity = K8sClusterFixtures.entity();
        when(repository.findByTenantIdAndId(K8sClusterFixtures.TENANT, K8sClusterFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(K8sClusterFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(K8sClusterFixtures.entity())));
        assertThat(service.search(K8sClusterFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        K8sClusterCreateRequest ok = K8sClusterFixtures.createRequest();
        K8sClusterCreateRequest dup = K8sClusterFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(K8sClusterFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(K8sClusterFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(K8sCluster.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<K8sClusterResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(K8sClusterFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(K8sClusterFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(K8sClusterFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
