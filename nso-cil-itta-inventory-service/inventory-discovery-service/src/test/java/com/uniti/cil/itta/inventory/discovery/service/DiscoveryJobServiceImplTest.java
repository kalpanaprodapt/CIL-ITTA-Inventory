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
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveryJobMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryJob;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveryJobRepository;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryJobFixtures;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveryJobValidator;
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
class DiscoveryJobServiceImplTest {

    @Mock
    private DiscoveryJobRepository repository;
    @Mock
    private EventPublisher events;

    private DiscoveryJobServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(DiscoveryJobFixtures.TENANT, "tester");
        service = new DiscoveryJobServiceImpl(repository, new DiscoveryJobMapper(), new DiscoveryJobValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(DiscoveryJobFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(DiscoveryJobFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(DiscoveryJobFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        DiscoveryJobCreateRequest request = DiscoveryJobFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveryJobFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(DiscoveryJob.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveryJobResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<DiscoveryJob> captor = ArgumentCaptor.forClass(DiscoveryJob.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(DiscoveryJobFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        DiscoveryJobCreateRequest request = DiscoveryJobFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveryJobFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(DiscoveryJob.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveryJobUpdateRequest request = new DiscoveryJobUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        DiscoveryJobResponse response = service.update(DiscoveryJobFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.of(entity));
        DiscoveryJobUpdateRequest request = new DiscoveryJobUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(DiscoveryJobFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(DiscoveryJobFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryJobFixtures.TENANT, DiscoveryJobFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(DiscoveryJobFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(DiscoveryJobFixtures.entity())));
        assertThat(service.search(DiscoveryJobFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        DiscoveryJobCreateRequest ok = DiscoveryJobFixtures.createRequest();
        DiscoveryJobCreateRequest dup = DiscoveryJobFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(DiscoveryJobFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(DiscoveryJobFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(DiscoveryJob.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<DiscoveryJobResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(DiscoveryJobFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(DiscoveryJobFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(DiscoveryJobFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
