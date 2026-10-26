package com.uniti.cil.itta.inventory.capacity.service;

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
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.mapper.CapacityPlanMapper;
import com.uniti.cil.itta.inventory.capacity.model.CapacityPlan;
import com.uniti.cil.itta.inventory.capacity.repository.CapacityPlanRepository;
import com.uniti.cil.itta.inventory.capacity.support.CapacityPlanFixtures;
import com.uniti.cil.itta.inventory.capacity.validation.CapacityPlanValidator;
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
class CapacityPlanServiceImplTest {

    @Mock
    private CapacityPlanRepository repository;
    @Mock
    private EventPublisher events;

    private CapacityPlanServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CapacityPlanFixtures.TENANT, "tester");
        service = new CapacityPlanServiceImpl(repository, new CapacityPlanMapper(), new CapacityPlanValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CapacityPlanFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CapacityPlanFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CapacityPlanFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CapacityPlanCreateRequest request = CapacityPlanFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CapacityPlanFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CapacityPlan.class))).thenAnswer(inv -> inv.getArgument(0));
        CapacityPlanResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CapacityPlan> captor = ArgumentCaptor.forClass(CapacityPlan.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CapacityPlanFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CapacityPlanCreateRequest request = CapacityPlanFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CapacityPlanFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CapacityPlan.class))).thenAnswer(inv -> inv.getArgument(0));
        CapacityPlanUpdateRequest request = new CapacityPlanUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CapacityPlanResponse response = service.update(CapacityPlanFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.of(entity));
        CapacityPlanUpdateRequest request = new CapacityPlanUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CapacityPlanFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CapacityPlanFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        when(repository.findByTenantIdAndId(CapacityPlanFixtures.TENANT, CapacityPlanFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CapacityPlanFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CapacityPlanFixtures.entity())));
        assertThat(service.search(CapacityPlanFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CapacityPlanCreateRequest ok = CapacityPlanFixtures.createRequest();
        CapacityPlanCreateRequest dup = CapacityPlanFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CapacityPlanFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CapacityPlanFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CapacityPlan.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CapacityPlanResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CapacityPlanFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CapacityPlanFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CapacityPlanFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
