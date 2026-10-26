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
import com.uniti.cil.itta.inventory.capacity.mapper.ThresholdPolicyMapper;
import com.uniti.cil.itta.inventory.capacity.model.ThresholdPolicy;
import com.uniti.cil.itta.inventory.capacity.repository.ThresholdPolicyRepository;
import com.uniti.cil.itta.inventory.capacity.support.ThresholdPolicyFixtures;
import com.uniti.cil.itta.inventory.capacity.validation.ThresholdPolicyValidator;
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
class ThresholdPolicyServiceImplTest {

    @Mock
    private ThresholdPolicyRepository repository;
    @Mock
    private EventPublisher events;

    private ThresholdPolicyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ThresholdPolicyFixtures.TENANT, "tester");
        service = new ThresholdPolicyServiceImpl(repository, new ThresholdPolicyMapper(), new ThresholdPolicyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ThresholdPolicyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ThresholdPolicyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ThresholdPolicyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ThresholdPolicyCreateRequest request = ThresholdPolicyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ThresholdPolicyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ThresholdPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        ThresholdPolicyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ThresholdPolicy> captor = ArgumentCaptor.forClass(ThresholdPolicy.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ThresholdPolicyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ThresholdPolicyCreateRequest request = ThresholdPolicyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ThresholdPolicyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ThresholdPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        ThresholdPolicyUpdateRequest request = new ThresholdPolicyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ThresholdPolicyResponse response = service.update(ThresholdPolicyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        ThresholdPolicyUpdateRequest request = new ThresholdPolicyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ThresholdPolicyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ThresholdPolicyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(ThresholdPolicyFixtures.TENANT, ThresholdPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ThresholdPolicyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ThresholdPolicyFixtures.entity())));
        assertThat(service.search(ThresholdPolicyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ThresholdPolicyCreateRequest ok = ThresholdPolicyFixtures.createRequest();
        ThresholdPolicyCreateRequest dup = ThresholdPolicyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ThresholdPolicyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ThresholdPolicyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ThresholdPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ThresholdPolicyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ThresholdPolicyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ThresholdPolicyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ThresholdPolicyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
