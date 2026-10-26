package com.uniti.cil.itta.inventory.topology.service;

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
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.mapper.TopologyEdgeMapper;
import com.uniti.cil.itta.inventory.topology.model.TopologyEdge;
import com.uniti.cil.itta.inventory.topology.repository.TopologyEdgeRepository;
import com.uniti.cil.itta.inventory.topology.support.TopologyEdgeFixtures;
import com.uniti.cil.itta.inventory.topology.validation.TopologyEdgeValidator;
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
class TopologyEdgeServiceImplTest {

    @Mock
    private TopologyEdgeRepository repository;
    @Mock
    private EventPublisher events;

    private TopologyEdgeServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TopologyEdgeFixtures.TENANT, "tester");
        service = new TopologyEdgeServiceImpl(repository, new TopologyEdgeMapper(), new TopologyEdgeValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TopologyEdgeFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TopologyEdgeFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TopologyEdgeFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TopologyEdgeCreateRequest request = TopologyEdgeFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TopologyEdgeFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(TopologyEdge.class))).thenAnswer(inv -> inv.getArgument(0));
        TopologyEdgeResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<TopologyEdge> captor = ArgumentCaptor.forClass(TopologyEdge.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TopologyEdgeFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TopologyEdgeCreateRequest request = TopologyEdgeFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TopologyEdgeFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(TopologyEdge.class))).thenAnswer(inv -> inv.getArgument(0));
        TopologyEdgeUpdateRequest request = new TopologyEdgeUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TopologyEdgeResponse response = service.update(TopologyEdgeFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.of(entity));
        TopologyEdgeUpdateRequest request = new TopologyEdgeUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TopologyEdgeFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TopologyEdgeFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyEdgeFixtures.TENANT, TopologyEdgeFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TopologyEdgeFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TopologyEdgeFixtures.entity())));
        assertThat(service.search(TopologyEdgeFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TopologyEdgeCreateRequest ok = TopologyEdgeFixtures.createRequest();
        TopologyEdgeCreateRequest dup = TopologyEdgeFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TopologyEdgeFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TopologyEdgeFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(TopologyEdge.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TopologyEdgeResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TopologyEdgeFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TopologyEdgeFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TopologyEdgeFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
