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
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitOrderMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitOrder;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitOrderRepository;
import com.uniti.cil.itta.inventory.circuit.support.CircuitOrderFixtures;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitOrderValidator;
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
class CircuitOrderServiceImplTest {

    @Mock
    private CircuitOrderRepository repository;
    @Mock
    private EventPublisher events;

    private CircuitOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CircuitOrderFixtures.TENANT, "tester");
        service = new CircuitOrderServiceImpl(repository, new CircuitOrderMapper(), new CircuitOrderValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CircuitOrderFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CircuitOrderFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CircuitOrderFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CircuitOrderCreateRequest request = CircuitOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitOrderFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CircuitOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitOrderResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CircuitOrder> captor = ArgumentCaptor.forClass(CircuitOrder.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CircuitOrderFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CircuitOrderCreateRequest request = CircuitOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitOrderFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CircuitOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitOrderUpdateRequest request = new CircuitOrderUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CircuitOrderResponse response = service.update(CircuitOrderFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.of(entity));
        CircuitOrderUpdateRequest request = new CircuitOrderUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CircuitOrderFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CircuitOrderFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitOrderFixtures.TENANT, CircuitOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CircuitOrderFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CircuitOrderFixtures.entity())));
        assertThat(service.search(CircuitOrderFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CircuitOrderCreateRequest ok = CircuitOrderFixtures.createRequest();
        CircuitOrderCreateRequest dup = CircuitOrderFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CircuitOrderFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CircuitOrderFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CircuitOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CircuitOrderResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CircuitOrderFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CircuitOrderFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CircuitOrderFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
