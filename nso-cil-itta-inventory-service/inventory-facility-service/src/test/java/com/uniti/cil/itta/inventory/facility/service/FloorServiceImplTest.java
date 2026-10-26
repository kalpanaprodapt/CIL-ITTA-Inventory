package com.uniti.cil.itta.inventory.facility.service;

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
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.mapper.FloorMapper;
import com.uniti.cil.itta.inventory.facility.model.Floor;
import com.uniti.cil.itta.inventory.facility.repository.FloorRepository;
import com.uniti.cil.itta.inventory.facility.support.FloorFixtures;
import com.uniti.cil.itta.inventory.facility.validation.FloorValidator;
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
class FloorServiceImplTest {

    @Mock
    private FloorRepository repository;
    @Mock
    private EventPublisher events;

    private FloorServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(FloorFixtures.TENANT, "tester");
        service = new FloorServiceImpl(repository, new FloorMapper(), new FloorValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Floor entity = FloorFixtures.entity();
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(FloorFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(FloorFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(FloorFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        FloorCreateRequest request = FloorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FloorFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Floor.class))).thenAnswer(inv -> inv.getArgument(0));
        FloorResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Floor> captor = ArgumentCaptor.forClass(Floor.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(FloorFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        FloorCreateRequest request = FloorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FloorFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Floor entity = FloorFixtures.entity();
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Floor.class))).thenAnswer(inv -> inv.getArgument(0));
        FloorUpdateRequest request = new FloorUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        FloorResponse response = service.update(FloorFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Floor entity = FloorFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.of(entity));
        FloorUpdateRequest request = new FloorUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(FloorFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Floor entity = FloorFixtures.entity();
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(FloorFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Floor entity = FloorFixtures.entity();
        when(repository.findByTenantIdAndId(FloorFixtures.TENANT, FloorFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(FloorFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(FloorFixtures.entity())));
        assertThat(service.search(FloorFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        FloorCreateRequest ok = FloorFixtures.createRequest();
        FloorCreateRequest dup = FloorFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(FloorFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(FloorFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Floor.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<FloorResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(FloorFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(FloorFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(FloorFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
