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
import com.uniti.cil.itta.inventory.assetmgmt.mapper.DepreciationScheduleMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.DepreciationSchedule;
import com.uniti.cil.itta.inventory.assetmgmt.repository.DepreciationScheduleRepository;
import com.uniti.cil.itta.inventory.assetmgmt.support.DepreciationScheduleFixtures;
import com.uniti.cil.itta.inventory.assetmgmt.validation.DepreciationScheduleValidator;
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
class DepreciationScheduleServiceImplTest {

    @Mock
    private DepreciationScheduleRepository repository;
    @Mock
    private EventPublisher events;

    private DepreciationScheduleServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(DepreciationScheduleFixtures.TENANT, "tester");
        service = new DepreciationScheduleServiceImpl(repository, new DepreciationScheduleMapper(), new DepreciationScheduleValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(DepreciationScheduleFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(DepreciationScheduleFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(DepreciationScheduleFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        DepreciationScheduleCreateRequest request = DepreciationScheduleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DepreciationScheduleFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(DepreciationSchedule.class))).thenAnswer(inv -> inv.getArgument(0));
        DepreciationScheduleResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<DepreciationSchedule> captor = ArgumentCaptor.forClass(DepreciationSchedule.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(DepreciationScheduleFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        DepreciationScheduleCreateRequest request = DepreciationScheduleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DepreciationScheduleFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(DepreciationSchedule.class))).thenAnswer(inv -> inv.getArgument(0));
        DepreciationScheduleUpdateRequest request = new DepreciationScheduleUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        DepreciationScheduleResponse response = service.update(DepreciationScheduleFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.of(entity));
        DepreciationScheduleUpdateRequest request = new DepreciationScheduleUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(DepreciationScheduleFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(DepreciationScheduleFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        when(repository.findByTenantIdAndId(DepreciationScheduleFixtures.TENANT, DepreciationScheduleFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(DepreciationScheduleFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(DepreciationScheduleFixtures.entity())));
        assertThat(service.search(DepreciationScheduleFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        DepreciationScheduleCreateRequest ok = DepreciationScheduleFixtures.createRequest();
        DepreciationScheduleCreateRequest dup = DepreciationScheduleFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(DepreciationScheduleFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(DepreciationScheduleFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(DepreciationSchedule.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<DepreciationScheduleResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(DepreciationScheduleFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(DepreciationScheduleFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(DepreciationScheduleFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
