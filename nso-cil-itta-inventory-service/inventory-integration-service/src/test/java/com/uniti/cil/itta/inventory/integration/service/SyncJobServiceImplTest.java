package com.uniti.cil.itta.inventory.integration.service;

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
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.mapper.SyncJobMapper;
import com.uniti.cil.itta.inventory.integration.model.SyncJob;
import com.uniti.cil.itta.inventory.integration.repository.SyncJobRepository;
import com.uniti.cil.itta.inventory.integration.support.SyncJobFixtures;
import com.uniti.cil.itta.inventory.integration.validation.SyncJobValidator;
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
class SyncJobServiceImplTest {

    @Mock
    private SyncJobRepository repository;
    @Mock
    private EventPublisher events;

    private SyncJobServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SyncJobFixtures.TENANT, "tester");
        service = new SyncJobServiceImpl(repository, new SyncJobMapper(), new SyncJobValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SyncJob entity = SyncJobFixtures.entity();
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SyncJobFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SyncJobFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SyncJobFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SyncJobCreateRequest request = SyncJobFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SyncJobFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SyncJob.class))).thenAnswer(inv -> inv.getArgument(0));
        SyncJobResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SyncJob> captor = ArgumentCaptor.forClass(SyncJob.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SyncJobFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SyncJobCreateRequest request = SyncJobFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SyncJobFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SyncJob entity = SyncJobFixtures.entity();
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SyncJob.class))).thenAnswer(inv -> inv.getArgument(0));
        SyncJobUpdateRequest request = new SyncJobUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SyncJobResponse response = service.update(SyncJobFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SyncJob entity = SyncJobFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.of(entity));
        SyncJobUpdateRequest request = new SyncJobUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SyncJobFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SyncJob entity = SyncJobFixtures.entity();
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SyncJobFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SyncJob entity = SyncJobFixtures.entity();
        when(repository.findByTenantIdAndId(SyncJobFixtures.TENANT, SyncJobFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SyncJobFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SyncJobFixtures.entity())));
        assertThat(service.search(SyncJobFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SyncJobCreateRequest ok = SyncJobFixtures.createRequest();
        SyncJobCreateRequest dup = SyncJobFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SyncJobFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SyncJobFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SyncJob.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SyncJobResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SyncJobFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SyncJobFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SyncJobFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
