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
import com.uniti.cil.itta.inventory.topology.mapper.TopologyViewMapper;
import com.uniti.cil.itta.inventory.topology.model.TopologyView;
import com.uniti.cil.itta.inventory.topology.repository.TopologyViewRepository;
import com.uniti.cil.itta.inventory.topology.support.TopologyViewFixtures;
import com.uniti.cil.itta.inventory.topology.validation.TopologyViewValidator;
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
class TopologyViewServiceImplTest {

    @Mock
    private TopologyViewRepository repository;
    @Mock
    private EventPublisher events;

    private TopologyViewServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TopologyViewFixtures.TENANT, "tester");
        service = new TopologyViewServiceImpl(repository, new TopologyViewMapper(), new TopologyViewValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        TopologyView entity = TopologyViewFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TopologyViewFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TopologyViewFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TopologyViewFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TopologyViewCreateRequest request = TopologyViewFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TopologyViewFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(TopologyView.class))).thenAnswer(inv -> inv.getArgument(0));
        TopologyViewResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<TopologyView> captor = ArgumentCaptor.forClass(TopologyView.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TopologyViewFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TopologyViewCreateRequest request = TopologyViewFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TopologyViewFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        TopologyView entity = TopologyViewFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(TopologyView.class))).thenAnswer(inv -> inv.getArgument(0));
        TopologyViewUpdateRequest request = new TopologyViewUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TopologyViewResponse response = service.update(TopologyViewFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        TopologyView entity = TopologyViewFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.of(entity));
        TopologyViewUpdateRequest request = new TopologyViewUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TopologyViewFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        TopologyView entity = TopologyViewFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TopologyViewFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        TopologyView entity = TopologyViewFixtures.entity();
        when(repository.findByTenantIdAndId(TopologyViewFixtures.TENANT, TopologyViewFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TopologyViewFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TopologyViewFixtures.entity())));
        assertThat(service.search(TopologyViewFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TopologyViewCreateRequest ok = TopologyViewFixtures.createRequest();
        TopologyViewCreateRequest dup = TopologyViewFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TopologyViewFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TopologyViewFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(TopologyView.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TopologyViewResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TopologyViewFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TopologyViewFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TopologyViewFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
