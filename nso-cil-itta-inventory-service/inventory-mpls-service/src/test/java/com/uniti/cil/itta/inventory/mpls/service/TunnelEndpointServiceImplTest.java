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
import com.uniti.cil.itta.inventory.mpls.mapper.TunnelEndpointMapper;
import com.uniti.cil.itta.inventory.mpls.model.TunnelEndpoint;
import com.uniti.cil.itta.inventory.mpls.repository.TunnelEndpointRepository;
import com.uniti.cil.itta.inventory.mpls.support.TunnelEndpointFixtures;
import com.uniti.cil.itta.inventory.mpls.validation.TunnelEndpointValidator;
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
class TunnelEndpointServiceImplTest {

    @Mock
    private TunnelEndpointRepository repository;
    @Mock
    private EventPublisher events;

    private TunnelEndpointServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TunnelEndpointFixtures.TENANT, "tester");
        service = new TunnelEndpointServiceImpl(repository, new TunnelEndpointMapper(), new TunnelEndpointValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TunnelEndpointFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TunnelEndpointFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TunnelEndpointFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TunnelEndpointCreateRequest request = TunnelEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TunnelEndpointFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(TunnelEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        TunnelEndpointResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<TunnelEndpoint> captor = ArgumentCaptor.forClass(TunnelEndpoint.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TunnelEndpointFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TunnelEndpointCreateRequest request = TunnelEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TunnelEndpointFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(TunnelEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        TunnelEndpointUpdateRequest request = new TunnelEndpointUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TunnelEndpointResponse response = service.update(TunnelEndpointFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        TunnelEndpointUpdateRequest request = new TunnelEndpointUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TunnelEndpointFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TunnelEndpointFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(TunnelEndpointFixtures.TENANT, TunnelEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TunnelEndpointFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TunnelEndpointFixtures.entity())));
        assertThat(service.search(TunnelEndpointFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TunnelEndpointCreateRequest ok = TunnelEndpointFixtures.createRequest();
        TunnelEndpointCreateRequest dup = TunnelEndpointFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TunnelEndpointFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TunnelEndpointFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(TunnelEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TunnelEndpointResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TunnelEndpointFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TunnelEndpointFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TunnelEndpointFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
