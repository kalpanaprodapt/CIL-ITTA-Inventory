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
import com.uniti.cil.itta.inventory.compliance.mapper.ComplianceCheckMapper;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceCheck;
import com.uniti.cil.itta.inventory.compliance.repository.ComplianceCheckRepository;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceCheckFixtures;
import com.uniti.cil.itta.inventory.compliance.validation.ComplianceCheckValidator;
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
class ComplianceCheckServiceImplTest {

    @Mock
    private ComplianceCheckRepository repository;
    @Mock
    private EventPublisher events;

    private ComplianceCheckServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ComplianceCheckFixtures.TENANT, "tester");
        service = new ComplianceCheckServiceImpl(repository, new ComplianceCheckMapper(), new ComplianceCheckValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ComplianceCheckFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ComplianceCheckFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ComplianceCheckFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ComplianceCheckCreateRequest request = ComplianceCheckFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceCheckFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ComplianceCheck.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceCheckResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ComplianceCheck> captor = ArgumentCaptor.forClass(ComplianceCheck.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ComplianceCheckFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ComplianceCheckCreateRequest request = ComplianceCheckFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceCheckFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ComplianceCheck.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceCheckUpdateRequest request = new ComplianceCheckUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ComplianceCheckResponse response = service.update(ComplianceCheckFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.of(entity));
        ComplianceCheckUpdateRequest request = new ComplianceCheckUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ComplianceCheckFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ComplianceCheckFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceCheckFixtures.TENANT, ComplianceCheckFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ComplianceCheckFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ComplianceCheckFixtures.entity())));
        assertThat(service.search(ComplianceCheckFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ComplianceCheckCreateRequest ok = ComplianceCheckFixtures.createRequest();
        ComplianceCheckCreateRequest dup = ComplianceCheckFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ComplianceCheckFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ComplianceCheckFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ComplianceCheck.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ComplianceCheckResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ComplianceCheckFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ComplianceCheckFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ComplianceCheckFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
