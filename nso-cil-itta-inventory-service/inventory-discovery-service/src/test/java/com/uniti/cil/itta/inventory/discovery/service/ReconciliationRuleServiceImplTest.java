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
import com.uniti.cil.itta.inventory.discovery.mapper.ReconciliationRuleMapper;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationRule;
import com.uniti.cil.itta.inventory.discovery.repository.ReconciliationRuleRepository;
import com.uniti.cil.itta.inventory.discovery.support.ReconciliationRuleFixtures;
import com.uniti.cil.itta.inventory.discovery.validation.ReconciliationRuleValidator;
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
class ReconciliationRuleServiceImplTest {

    @Mock
    private ReconciliationRuleRepository repository;
    @Mock
    private EventPublisher events;

    private ReconciliationRuleServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ReconciliationRuleFixtures.TENANT, "tester");
        service = new ReconciliationRuleServiceImpl(repository, new ReconciliationRuleMapper(), new ReconciliationRuleValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ReconciliationRuleFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ReconciliationRuleFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ReconciliationRuleFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ReconciliationRuleCreateRequest request = ReconciliationRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReconciliationRuleFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ReconciliationRule.class))).thenAnswer(inv -> inv.getArgument(0));
        ReconciliationRuleResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ReconciliationRule> captor = ArgumentCaptor.forClass(ReconciliationRule.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ReconciliationRuleFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ReconciliationRuleCreateRequest request = ReconciliationRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReconciliationRuleFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ReconciliationRule.class))).thenAnswer(inv -> inv.getArgument(0));
        ReconciliationRuleUpdateRequest request = new ReconciliationRuleUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ReconciliationRuleResponse response = service.update(ReconciliationRuleFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.of(entity));
        ReconciliationRuleUpdateRequest request = new ReconciliationRuleUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ReconciliationRuleFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ReconciliationRuleFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ReconciliationRuleFixtures.TENANT, ReconciliationRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ReconciliationRuleFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ReconciliationRuleFixtures.entity())));
        assertThat(service.search(ReconciliationRuleFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ReconciliationRuleCreateRequest ok = ReconciliationRuleFixtures.createRequest();
        ReconciliationRuleCreateRequest dup = ReconciliationRuleFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ReconciliationRuleFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ReconciliationRuleFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ReconciliationRule.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ReconciliationRuleResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ReconciliationRuleFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ReconciliationRuleFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ReconciliationRuleFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
