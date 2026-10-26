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
import com.uniti.cil.itta.inventory.facility.mapper.RoomMapper;
import com.uniti.cil.itta.inventory.facility.model.Room;
import com.uniti.cil.itta.inventory.facility.repository.RoomRepository;
import com.uniti.cil.itta.inventory.facility.support.RoomFixtures;
import com.uniti.cil.itta.inventory.facility.validation.RoomValidator;
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
class RoomServiceImplTest {

    @Mock
    private RoomRepository repository;
    @Mock
    private EventPublisher events;

    private RoomServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RoomFixtures.TENANT, "tester");
        service = new RoomServiceImpl(repository, new RoomMapper(), new RoomValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Room entity = RoomFixtures.entity();
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RoomFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RoomFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RoomFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RoomCreateRequest request = RoomFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RoomFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Room.class))).thenAnswer(inv -> inv.getArgument(0));
        RoomResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RoomFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RoomCreateRequest request = RoomFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RoomFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Room entity = RoomFixtures.entity();
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Room.class))).thenAnswer(inv -> inv.getArgument(0));
        RoomUpdateRequest request = new RoomUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RoomResponse response = service.update(RoomFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Room entity = RoomFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.of(entity));
        RoomUpdateRequest request = new RoomUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RoomFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Room entity = RoomFixtures.entity();
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RoomFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Room entity = RoomFixtures.entity();
        when(repository.findByTenantIdAndId(RoomFixtures.TENANT, RoomFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RoomFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RoomFixtures.entity())));
        assertThat(service.search(RoomFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RoomCreateRequest ok = RoomFixtures.createRequest();
        RoomCreateRequest dup = RoomFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RoomFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RoomFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Room.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RoomResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RoomFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RoomFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RoomFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
