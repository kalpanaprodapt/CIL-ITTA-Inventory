package com.uniti.cil.itta.inventory.integration.service;

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
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.mapper.IntegrationEndpointMapper;
import com.uniti.cil.itta.inventory.integration.model.IntegrationEndpoint;
import com.uniti.cil.itta.inventory.integration.repository.IntegrationEndpointRepository;
import com.uniti.cil.itta.inventory.integration.support.IntegrationEndpointFixtures;
import com.uniti.cil.itta.inventory.integration.validation.IntegrationEndpointValidator;
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
class IntegrationEndpointServiceImplTest {

    @Mock
    private IntegrationEndpointRepository repository;
    @Mock
    private EventPublisher events;

    private IntegrationEndpointServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(IntegrationEndpointFixtures.TENANT, "tester");
        service = new IntegrationEndpointServiceImpl(repository, new IntegrationEndpointMapper(), new IntegrationEndpointValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(IntegrationEndpointFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(IntegrationEndpointFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        IntegrationEndpointCreateRequest request = IntegrationEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(IntegrationEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        IntegrationEndpointResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<IntegrationEndpoint> captor = ArgumentCaptor.forClass(IntegrationEndpoint.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(IntegrationEndpointFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        IntegrationEndpointCreateRequest request = IntegrationEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(IntegrationEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        IntegrationEndpointUpdateRequest request = new IntegrationEndpointUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        IntegrationEndpointResponse response = service.update(IntegrationEndpointFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        IntegrationEndpointUpdateRequest request = new IntegrationEndpointUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(IntegrationEndpointFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(IntegrationEndpointFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(IntegrationEndpointFixtures.TENANT, IntegrationEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(IntegrationEndpointFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(IntegrationEndpointFixtures.entity())));
        assertThat(service.search(IntegrationEndpointFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        IntegrationEndpointCreateRequest ok = IntegrationEndpointFixtures.createRequest();
        IntegrationEndpointCreateRequest dup = IntegrationEndpointFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(IntegrationEndpointFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(IntegrationEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<IntegrationEndpointResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(IntegrationEndpointFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(IntegrationEndpointFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(IntegrationEndpointFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
