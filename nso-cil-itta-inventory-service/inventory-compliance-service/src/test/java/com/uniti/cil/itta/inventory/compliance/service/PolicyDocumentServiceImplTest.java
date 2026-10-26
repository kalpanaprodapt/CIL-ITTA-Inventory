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
import com.uniti.cil.itta.inventory.compliance.mapper.PolicyDocumentMapper;
import com.uniti.cil.itta.inventory.compliance.model.PolicyDocument;
import com.uniti.cil.itta.inventory.compliance.repository.PolicyDocumentRepository;
import com.uniti.cil.itta.inventory.compliance.support.PolicyDocumentFixtures;
import com.uniti.cil.itta.inventory.compliance.validation.PolicyDocumentValidator;
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
class PolicyDocumentServiceImplTest {

    @Mock
    private PolicyDocumentRepository repository;
    @Mock
    private EventPublisher events;

    private PolicyDocumentServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PolicyDocumentFixtures.TENANT, "tester");
        service = new PolicyDocumentServiceImpl(repository, new PolicyDocumentMapper(), new PolicyDocumentValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PolicyDocumentFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PolicyDocumentFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PolicyDocumentFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PolicyDocumentCreateRequest request = PolicyDocumentFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PolicyDocumentFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PolicyDocument.class))).thenAnswer(inv -> inv.getArgument(0));
        PolicyDocumentResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PolicyDocument> captor = ArgumentCaptor.forClass(PolicyDocument.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PolicyDocumentFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PolicyDocumentCreateRequest request = PolicyDocumentFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PolicyDocumentFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PolicyDocument.class))).thenAnswer(inv -> inv.getArgument(0));
        PolicyDocumentUpdateRequest request = new PolicyDocumentUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PolicyDocumentResponse response = service.update(PolicyDocumentFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.of(entity));
        PolicyDocumentUpdateRequest request = new PolicyDocumentUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PolicyDocumentFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PolicyDocumentFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        when(repository.findByTenantIdAndId(PolicyDocumentFixtures.TENANT, PolicyDocumentFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PolicyDocumentFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PolicyDocumentFixtures.entity())));
        assertThat(service.search(PolicyDocumentFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PolicyDocumentCreateRequest ok = PolicyDocumentFixtures.createRequest();
        PolicyDocumentCreateRequest dup = PolicyDocumentFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PolicyDocumentFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PolicyDocumentFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PolicyDocument.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PolicyDocumentResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PolicyDocumentFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PolicyDocumentFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PolicyDocumentFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
