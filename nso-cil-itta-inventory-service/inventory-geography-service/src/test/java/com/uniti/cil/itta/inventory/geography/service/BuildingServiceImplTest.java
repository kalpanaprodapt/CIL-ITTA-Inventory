package com.uniti.cil.itta.inventory.geography.service;

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
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.mapper.BuildingMapper;
import com.uniti.cil.itta.inventory.geography.model.Building;
import com.uniti.cil.itta.inventory.geography.repository.BuildingRepository;
import com.uniti.cil.itta.inventory.geography.support.BuildingFixtures;
import com.uniti.cil.itta.inventory.geography.validation.BuildingValidator;
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
class BuildingServiceImplTest {

    @Mock
    private BuildingRepository repository;
    @Mock
    private EventPublisher events;

    private BuildingServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(BuildingFixtures.TENANT, "tester");
        service = new BuildingServiceImpl(repository, new BuildingMapper(), new BuildingValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Building entity = BuildingFixtures.entity();
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(BuildingFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(BuildingFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(BuildingFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        BuildingCreateRequest request = BuildingFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BuildingFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Building.class))).thenAnswer(inv -> inv.getArgument(0));
        BuildingResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Building> captor = ArgumentCaptor.forClass(Building.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(BuildingFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        BuildingCreateRequest request = BuildingFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BuildingFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Building entity = BuildingFixtures.entity();
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Building.class))).thenAnswer(inv -> inv.getArgument(0));
        BuildingUpdateRequest request = new BuildingUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        BuildingResponse response = service.update(BuildingFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Building entity = BuildingFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.of(entity));
        BuildingUpdateRequest request = new BuildingUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(BuildingFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Building entity = BuildingFixtures.entity();
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(BuildingFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Building entity = BuildingFixtures.entity();
        when(repository.findByTenantIdAndId(BuildingFixtures.TENANT, BuildingFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(BuildingFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(BuildingFixtures.entity())));
        assertThat(service.search(BuildingFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        BuildingCreateRequest ok = BuildingFixtures.createRequest();
        BuildingCreateRequest dup = BuildingFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(BuildingFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(BuildingFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Building.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<BuildingResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(BuildingFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(BuildingFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(BuildingFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
