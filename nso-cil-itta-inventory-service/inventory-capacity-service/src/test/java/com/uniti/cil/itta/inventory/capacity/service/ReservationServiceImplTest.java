package com.uniti.cil.itta.inventory.capacity.service;

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
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.mapper.ReservationMapper;
import com.uniti.cil.itta.inventory.capacity.model.Reservation;
import com.uniti.cil.itta.inventory.capacity.repository.ReservationRepository;
import com.uniti.cil.itta.inventory.capacity.support.ReservationFixtures;
import com.uniti.cil.itta.inventory.capacity.validation.ReservationValidator;
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
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository repository;
    @Mock
    private EventPublisher events;

    private ReservationServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ReservationFixtures.TENANT, "tester");
        service = new ReservationServiceImpl(repository, new ReservationMapper(), new ReservationValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Reservation entity = ReservationFixtures.entity();
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ReservationFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ReservationFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ReservationFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ReservationCreateRequest request = ReservationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReservationFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));
        ReservationResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ReservationFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ReservationCreateRequest request = ReservationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ReservationFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Reservation entity = ReservationFixtures.entity();
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));
        ReservationUpdateRequest request = new ReservationUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ReservationResponse response = service.update(ReservationFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Reservation entity = ReservationFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.of(entity));
        ReservationUpdateRequest request = new ReservationUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ReservationFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Reservation entity = ReservationFixtures.entity();
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ReservationFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Reservation entity = ReservationFixtures.entity();
        when(repository.findByTenantIdAndId(ReservationFixtures.TENANT, ReservationFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ReservationFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ReservationFixtures.entity())));
        assertThat(service.search(ReservationFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ReservationCreateRequest ok = ReservationFixtures.createRequest();
        ReservationCreateRequest dup = ReservationFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ReservationFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ReservationFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ReservationResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ReservationFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ReservationFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ReservationFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
