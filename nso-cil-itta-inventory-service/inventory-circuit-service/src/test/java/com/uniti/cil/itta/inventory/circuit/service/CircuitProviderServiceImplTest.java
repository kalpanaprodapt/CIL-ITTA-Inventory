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
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitProviderMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitProvider;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitProviderRepository;
import com.uniti.cil.itta.inventory.circuit.support.CircuitProviderFixtures;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitProviderValidator;
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
class CircuitProviderServiceImplTest {

    @Mock
    private CircuitProviderRepository repository;
    @Mock
    private EventPublisher events;

    private CircuitProviderServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CircuitProviderFixtures.TENANT, "tester");
        service = new CircuitProviderServiceImpl(repository, new CircuitProviderMapper(), new CircuitProviderValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CircuitProviderFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CircuitProviderFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CircuitProviderFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CircuitProviderCreateRequest request = CircuitProviderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitProviderFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CircuitProvider.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitProviderResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CircuitProvider> captor = ArgumentCaptor.forClass(CircuitProvider.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CircuitProviderFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CircuitProviderCreateRequest request = CircuitProviderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CircuitProviderFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CircuitProvider.class))).thenAnswer(inv -> inv.getArgument(0));
        CircuitProviderUpdateRequest request = new CircuitProviderUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CircuitProviderResponse response = service.update(CircuitProviderFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.of(entity));
        CircuitProviderUpdateRequest request = new CircuitProviderUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CircuitProviderFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CircuitProviderFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        when(repository.findByTenantIdAndId(CircuitProviderFixtures.TENANT, CircuitProviderFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CircuitProviderFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CircuitProviderFixtures.entity())));
        assertThat(service.search(CircuitProviderFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CircuitProviderCreateRequest ok = CircuitProviderFixtures.createRequest();
        CircuitProviderCreateRequest dup = CircuitProviderFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CircuitProviderFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CircuitProviderFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CircuitProvider.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CircuitProviderResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CircuitProviderFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CircuitProviderFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CircuitProviderFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
