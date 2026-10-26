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
import com.uniti.cil.itta.inventory.compliance.mapper.ComplianceExceptionMapper;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceException;
import com.uniti.cil.itta.inventory.compliance.repository.ComplianceExceptionRepository;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceExceptionFixtures;
import com.uniti.cil.itta.inventory.compliance.validation.ComplianceExceptionValidator;
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
class ComplianceExceptionServiceImplTest {

    @Mock
    private ComplianceExceptionRepository repository;
    @Mock
    private EventPublisher events;

    private ComplianceExceptionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ComplianceExceptionFixtures.TENANT, "tester");
        service = new ComplianceExceptionServiceImpl(repository, new ComplianceExceptionMapper(), new ComplianceExceptionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ComplianceExceptionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ComplianceExceptionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ComplianceExceptionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ComplianceExceptionCreateRequest request = ComplianceExceptionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceExceptionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ComplianceException.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceExceptionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ComplianceException> captor = ArgumentCaptor.forClass(ComplianceException.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ComplianceExceptionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ComplianceExceptionCreateRequest request = ComplianceExceptionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ComplianceExceptionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ComplianceException.class))).thenAnswer(inv -> inv.getArgument(0));
        ComplianceExceptionUpdateRequest request = new ComplianceExceptionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ComplianceExceptionResponse response = service.update(ComplianceExceptionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.of(entity));
        ComplianceExceptionUpdateRequest request = new ComplianceExceptionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ComplianceExceptionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ComplianceExceptionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        when(repository.findByTenantIdAndId(ComplianceExceptionFixtures.TENANT, ComplianceExceptionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ComplianceExceptionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ComplianceExceptionFixtures.entity())));
        assertThat(service.search(ComplianceExceptionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ComplianceExceptionCreateRequest ok = ComplianceExceptionFixtures.createRequest();
        ComplianceExceptionCreateRequest dup = ComplianceExceptionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ComplianceExceptionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ComplianceExceptionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ComplianceException.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ComplianceExceptionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ComplianceExceptionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ComplianceExceptionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ComplianceExceptionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
