package com.uniti.cil.itta.inventory.optical.service;

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
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.mapper.RoadmMapper;
import com.uniti.cil.itta.inventory.optical.model.Roadm;
import com.uniti.cil.itta.inventory.optical.repository.RoadmRepository;
import com.uniti.cil.itta.inventory.optical.support.RoadmFixtures;
import com.uniti.cil.itta.inventory.optical.validation.RoadmValidator;
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
class RoadmServiceImplTest {

    @Mock
    private RoadmRepository repository;
    @Mock
    private EventPublisher events;

    private RoadmServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RoadmFixtures.TENANT, "tester");
        service = new RoadmServiceImpl(repository, new RoadmMapper(), new RoadmValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Roadm entity = RoadmFixtures.entity();
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RoadmFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RoadmFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RoadmFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RoadmCreateRequest request = RoadmFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RoadmFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Roadm.class))).thenAnswer(inv -> inv.getArgument(0));
        RoadmResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Roadm> captor = ArgumentCaptor.forClass(Roadm.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RoadmFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RoadmCreateRequest request = RoadmFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RoadmFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Roadm entity = RoadmFixtures.entity();
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Roadm.class))).thenAnswer(inv -> inv.getArgument(0));
        RoadmUpdateRequest request = new RoadmUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RoadmResponse response = service.update(RoadmFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Roadm entity = RoadmFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.of(entity));
        RoadmUpdateRequest request = new RoadmUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RoadmFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Roadm entity = RoadmFixtures.entity();
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RoadmFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Roadm entity = RoadmFixtures.entity();
        when(repository.findByTenantIdAndId(RoadmFixtures.TENANT, RoadmFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RoadmFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RoadmFixtures.entity())));
        assertThat(service.search(RoadmFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RoadmCreateRequest ok = RoadmFixtures.createRequest();
        RoadmCreateRequest dup = RoadmFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RoadmFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RoadmFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Roadm.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RoadmResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RoadmFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RoadmFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RoadmFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
