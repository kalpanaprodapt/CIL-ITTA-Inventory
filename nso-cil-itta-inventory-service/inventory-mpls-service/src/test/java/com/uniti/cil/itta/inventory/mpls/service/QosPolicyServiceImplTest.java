package com.uniti.cil.itta.inventory.mpls.service;

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
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.mapper.QosPolicyMapper;
import com.uniti.cil.itta.inventory.mpls.model.QosPolicy;
import com.uniti.cil.itta.inventory.mpls.repository.QosPolicyRepository;
import com.uniti.cil.itta.inventory.mpls.support.QosPolicyFixtures;
import com.uniti.cil.itta.inventory.mpls.validation.QosPolicyValidator;
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
class QosPolicyServiceImplTest {

    @Mock
    private QosPolicyRepository repository;
    @Mock
    private EventPublisher events;

    private QosPolicyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(QosPolicyFixtures.TENANT, "tester");
        service = new QosPolicyServiceImpl(repository, new QosPolicyMapper(), new QosPolicyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        QosPolicy entity = QosPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(QosPolicyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(QosPolicyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(QosPolicyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        QosPolicyCreateRequest request = QosPolicyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(QosPolicyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(QosPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        QosPolicyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<QosPolicy> captor = ArgumentCaptor.forClass(QosPolicy.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(QosPolicyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        QosPolicyCreateRequest request = QosPolicyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(QosPolicyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        QosPolicy entity = QosPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(QosPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        QosPolicyUpdateRequest request = new QosPolicyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        QosPolicyResponse response = service.update(QosPolicyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        QosPolicy entity = QosPolicyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        QosPolicyUpdateRequest request = new QosPolicyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(QosPolicyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        QosPolicy entity = QosPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(QosPolicyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        QosPolicy entity = QosPolicyFixtures.entity();
        when(repository.findByTenantIdAndId(QosPolicyFixtures.TENANT, QosPolicyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(QosPolicyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(QosPolicyFixtures.entity())));
        assertThat(service.search(QosPolicyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        QosPolicyCreateRequest ok = QosPolicyFixtures.createRequest();
        QosPolicyCreateRequest dup = QosPolicyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(QosPolicyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(QosPolicyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(QosPolicy.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<QosPolicyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(QosPolicyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(QosPolicyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(QosPolicyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
