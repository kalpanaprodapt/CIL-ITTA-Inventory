package com.uniti.cil.itta.inventory.circuit.service;

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
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitSegmentMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitSegment;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitSegmentRepository;
import com.uniti.cil.itta.inventory.circuit.support.CircuitSegmentFixtures;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitSegmentValidator;
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
class CircuitSegmentServiceImplTest {

    @Mock
    private CircuitSegmentRepository repository;
    @Mock
    private EventPublisher events;

    private CircuitSegmentServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CircuitSegmentFixtures.TENANT, "tester");
        service = new CircuitSegmentServiceImpl(repository, new CircuitSegmentMapper(), new CircuitSegmentValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CircuitSegmentFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CircuitSegmentFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CircuitSegmentFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CircuitSegmentCreateRequest request = CircuitSegmentFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitSegmentFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CircuitSegment.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitSegmentResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CircuitSegment> captor = ArgumentCaptor.forClass(CircuitSegment.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CircuitSegmentFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CircuitSegmentCreateRequest request = CircuitSegmentFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitSegmentFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CircuitSegment.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitSegmentUpdateRequest request = new CircuitSegmentUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CircuitSegmentResponse response = service.update(CircuitSegmentFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.of(entity));
        CircuitSegmentUpdateRequest request = new CircuitSegmentUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CircuitSegmentFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CircuitSegmentFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitSegmentFixtures.TENANT, CircuitSegmentFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CircuitSegmentFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CircuitSegmentFixtures.entity())));
        assertThat(service.search(CircuitSegmentFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CircuitSegmentCreateRequest ok = CircuitSegmentFixtures.createRequest();
        CircuitSegmentCreateRequest dup = CircuitSegmentFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CircuitSegmentFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CircuitSegmentFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CircuitSegment.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CircuitSegmentResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CircuitSegmentFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CircuitSegmentFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CircuitSegmentFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
