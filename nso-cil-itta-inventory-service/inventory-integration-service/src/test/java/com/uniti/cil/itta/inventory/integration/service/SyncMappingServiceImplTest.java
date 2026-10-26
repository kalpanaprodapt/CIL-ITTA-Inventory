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
import com.uniti.cil.itta.inventory.integration.mapper.SyncMappingMapper;
import com.uniti.cil.itta.inventory.integration.model.SyncMapping;
import com.uniti.cil.itta.inventory.integration.repository.SyncMappingRepository;
import com.uniti.cil.itta.inventory.integration.support.SyncMappingFixtures;
import com.uniti.cil.itta.inventory.integration.validation.SyncMappingValidator;
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
class SyncMappingServiceImplTest {

    @Mock
    private SyncMappingRepository repository;
    @Mock
    private EventPublisher events;

    private SyncMappingServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SyncMappingFixtures.TENANT, "tester");
        service = new SyncMappingServiceImpl(repository, new SyncMappingMapper(), new SyncMappingValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SyncMapping entity = SyncMappingFixtures.entity();
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SyncMappingFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SyncMappingFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SyncMappingFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SyncMappingCreateRequest request = SyncMappingFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SyncMappingFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SyncMapping.class))).thenAnswer(inv -> inv.getArgument(0));
        SyncMappingResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SyncMapping> captor = ArgumentCaptor.forClass(SyncMapping.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SyncMappingFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SyncMappingCreateRequest request = SyncMappingFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SyncMappingFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SyncMapping entity = SyncMappingFixtures.entity();
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SyncMapping.class))).thenAnswer(inv -> inv.getArgument(0));
        SyncMappingUpdateRequest request = new SyncMappingUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SyncMappingResponse response = service.update(SyncMappingFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SyncMapping entity = SyncMappingFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.of(entity));
        SyncMappingUpdateRequest request = new SyncMappingUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SyncMappingFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SyncMapping entity = SyncMappingFixtures.entity();
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SyncMappingFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SyncMapping entity = SyncMappingFixtures.entity();
        when(repository.findByTenantIdAndId(SyncMappingFixtures.TENANT, SyncMappingFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SyncMappingFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SyncMappingFixtures.entity())));
        assertThat(service.search(SyncMappingFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SyncMappingCreateRequest ok = SyncMappingFixtures.createRequest();
        SyncMappingCreateRequest dup = SyncMappingFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SyncMappingFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SyncMappingFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SyncMapping.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SyncMappingResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SyncMappingFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SyncMappingFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SyncMappingFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
