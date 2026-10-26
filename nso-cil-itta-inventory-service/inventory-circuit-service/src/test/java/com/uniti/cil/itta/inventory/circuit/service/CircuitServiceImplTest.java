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
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitMapper;
import com.uniti.cil.itta.inventory.circuit.model.Circuit;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitRepository;
import com.uniti.cil.itta.inventory.circuit.support.CircuitFixtures;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitValidator;
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
class CircuitServiceImplTest {

    @Mock
    private CircuitRepository repository;
    @Mock
    private EventPublisher events;

    private CircuitServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CircuitFixtures.TENANT, "tester");
        service = new CircuitServiceImpl(repository, new CircuitMapper(), new CircuitValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Circuit entity = CircuitFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CircuitFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CircuitFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CircuitFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CircuitCreateRequest request = CircuitFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Circuit.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Circuit> captor = ArgumentCaptor.forClass(Circuit.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CircuitFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CircuitCreateRequest request = CircuitFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Circuit entity = CircuitFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Circuit.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitUpdateRequest request = new CircuitUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CircuitResponse response = service.update(CircuitFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Circuit entity = CircuitFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.of(entity));
        CircuitUpdateRequest request = new CircuitUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CircuitFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Circuit entity = CircuitFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CircuitFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Circuit entity = CircuitFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitFixtures.TENANT, CircuitFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CircuitFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CircuitFixtures.entity())));
        assertThat(service.search(CircuitFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CircuitCreateRequest ok = CircuitFixtures.createRequest();
        CircuitCreateRequest dup = CircuitFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CircuitFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CircuitFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Circuit.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CircuitResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CircuitFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CircuitFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CircuitFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
