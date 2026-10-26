package com.uniti.cil.itta.inventory.alarm.service;

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
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.mapper.SuppressionRuleMapper;
import com.uniti.cil.itta.inventory.alarm.model.SuppressionRule;
import com.uniti.cil.itta.inventory.alarm.repository.SuppressionRuleRepository;
import com.uniti.cil.itta.inventory.alarm.support.SuppressionRuleFixtures;
import com.uniti.cil.itta.inventory.alarm.validation.SuppressionRuleValidator;
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
class SuppressionRuleServiceImplTest {

    @Mock
    private SuppressionRuleRepository repository;
    @Mock
    private EventPublisher events;

    private SuppressionRuleServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SuppressionRuleFixtures.TENANT, "tester");
        service = new SuppressionRuleServiceImpl(repository, new SuppressionRuleMapper(), new SuppressionRuleValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SuppressionRuleFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SuppressionRuleFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SuppressionRuleFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SuppressionRuleCreateRequest request = SuppressionRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SuppressionRuleFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SuppressionRule.class))).thenAnswer(inv -> inv.getArgument(0));
        SuppressionRuleResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SuppressionRule> captor = ArgumentCaptor.forClass(SuppressionRule.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SuppressionRuleFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SuppressionRuleCreateRequest request = SuppressionRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SuppressionRuleFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SuppressionRule.class))).thenAnswer(inv -> inv.getArgument(0));
        SuppressionRuleUpdateRequest request = new SuppressionRuleUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SuppressionRuleResponse response = service.update(SuppressionRuleFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.of(entity));
        SuppressionRuleUpdateRequest request = new SuppressionRuleUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SuppressionRuleFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SuppressionRuleFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        when(repository.findByTenantIdAndId(SuppressionRuleFixtures.TENANT, SuppressionRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SuppressionRuleFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SuppressionRuleFixtures.entity())));
        assertThat(service.search(SuppressionRuleFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SuppressionRuleCreateRequest ok = SuppressionRuleFixtures.createRequest();
        SuppressionRuleCreateRequest dup = SuppressionRuleFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SuppressionRuleFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SuppressionRuleFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SuppressionRule.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SuppressionRuleResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SuppressionRuleFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SuppressionRuleFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SuppressionRuleFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
