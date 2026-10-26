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
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitTerminationMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitTermination;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitTerminationRepository;
import com.uniti.cil.itta.inventory.circuit.support.CircuitTerminationFixtures;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitTerminationValidator;
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
class CircuitTerminationServiceImplTest {

    @Mock
    private CircuitTerminationRepository repository;
    @Mock
    private EventPublisher events;

    private CircuitTerminationServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CircuitTerminationFixtures.TENANT, "tester");
        service = new CircuitTerminationServiceImpl(repository, new CircuitTerminationMapper(), new CircuitTerminationValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CircuitTerminationFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CircuitTerminationFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CircuitTerminationFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CircuitTerminationCreateRequest request = CircuitTerminationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitTerminationFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CircuitTermination.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitTerminationResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CircuitTermination> captor = ArgumentCaptor.forClass(CircuitTermination.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CircuitTerminationFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CircuitTerminationCreateRequest request = CircuitTerminationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitTerminationFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CircuitTermination.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitTerminationUpdateRequest request = new CircuitTerminationUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CircuitTerminationResponse response = service.update(CircuitTerminationFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.of(entity));
        CircuitTerminationUpdateRequest request = new CircuitTerminationUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CircuitTerminationFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CircuitTerminationFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitTerminationFixtures.TENANT, CircuitTerminationFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CircuitTerminationFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CircuitTerminationFixtures.entity())));
        assertThat(service.search(CircuitTerminationFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CircuitTerminationCreateRequest ok = CircuitTerminationFixtures.createRequest();
        CircuitTerminationCreateRequest dup = CircuitTerminationFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CircuitTerminationFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CircuitTerminationFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CircuitTermination.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CircuitTerminationResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CircuitTerminationFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CircuitTerminationFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CircuitTerminationFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
