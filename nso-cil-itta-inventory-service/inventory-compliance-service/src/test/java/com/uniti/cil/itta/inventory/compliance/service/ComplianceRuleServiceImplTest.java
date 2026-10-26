package com.uniti.cil.itta.inventory.compliance.service;

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
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.mapper.ComplianceRuleMapper;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceRule;
import com.uniti.cil.itta.inventory.compliance.repository.ComplianceRuleRepository;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceRuleFixtures;
import com.uniti.cil.itta.inventory.compliance.validation.ComplianceRuleValidator;
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
class ComplianceRuleServiceImplTest {

    @Mock
    private ComplianceRuleRepository repository;
    @Mock
    private EventPublisher events;

    private ComplianceRuleServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ComplianceRuleFixtures.TENANT, "tester");
        service = new ComplianceRuleServiceImpl(repository, new ComplianceRuleMapper(), new ComplianceRuleValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ComplianceRuleFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ComplianceRuleFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ComplianceRuleFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ComplianceRuleCreateRequest request = ComplianceRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceRuleFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ComplianceRule.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceRuleResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ComplianceRule> captor = ArgumentCaptor.forClass(ComplianceRule.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ComplianceRuleFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ComplianceRuleCreateRequest request = ComplianceRuleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceRuleFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ComplianceRule.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceRuleUpdateRequest request = new ComplianceRuleUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ComplianceRuleResponse response = service.update(ComplianceRuleFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.of(entity));
        ComplianceRuleUpdateRequest request = new ComplianceRuleUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ComplianceRuleFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ComplianceRuleFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceRuleFixtures.TENANT, ComplianceRuleFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ComplianceRuleFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ComplianceRuleFixtures.entity())));
        assertThat(service.search(ComplianceRuleFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ComplianceRuleCreateRequest ok = ComplianceRuleFixtures.createRequest();
        ComplianceRuleCreateRequest dup = ComplianceRuleFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ComplianceRuleFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ComplianceRuleFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ComplianceRule.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ComplianceRuleResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ComplianceRuleFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ComplianceRuleFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ComplianceRuleFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
