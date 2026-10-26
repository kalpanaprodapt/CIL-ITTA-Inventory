package com.uniti.cil.itta.inventory.switching.service;

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
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.mapper.RouteTargetMapper;
import com.uniti.cil.itta.inventory.switching.model.RouteTarget;
import com.uniti.cil.itta.inventory.switching.repository.RouteTargetRepository;
import com.uniti.cil.itta.inventory.switching.support.RouteTargetFixtures;
import com.uniti.cil.itta.inventory.switching.validation.RouteTargetValidator;
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
class RouteTargetServiceImplTest {

    @Mock
    private RouteTargetRepository repository;
    @Mock
    private EventPublisher events;

    private RouteTargetServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RouteTargetFixtures.TENANT, "tester");
        service = new RouteTargetServiceImpl(repository, new RouteTargetMapper(), new RouteTargetValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        RouteTarget entity = RouteTargetFixtures.entity();
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RouteTargetFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RouteTargetFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RouteTargetFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RouteTargetCreateRequest request = RouteTargetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RouteTargetFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(RouteTarget.class))).thenAnswer(inv -> inv.getArgument(0));
        RouteTargetResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<RouteTarget> captor = ArgumentCaptor.forClass(RouteTarget.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RouteTargetFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RouteTargetCreateRequest request = RouteTargetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RouteTargetFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        RouteTarget entity = RouteTargetFixtures.entity();
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(RouteTarget.class))).thenAnswer(inv -> inv.getArgument(0));
        RouteTargetUpdateRequest request = new RouteTargetUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RouteTargetResponse response = service.update(RouteTargetFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        RouteTarget entity = RouteTargetFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.of(entity));
        RouteTargetUpdateRequest request = new RouteTargetUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RouteTargetFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        RouteTarget entity = RouteTargetFixtures.entity();
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RouteTargetFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        RouteTarget entity = RouteTargetFixtures.entity();
        when(repository.findByTenantIdAndId(RouteTargetFixtures.TENANT, RouteTargetFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RouteTargetFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RouteTargetFixtures.entity())));
        assertThat(service.search(RouteTargetFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RouteTargetCreateRequest ok = RouteTargetFixtures.createRequest();
        RouteTargetCreateRequest dup = RouteTargetFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RouteTargetFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RouteTargetFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(RouteTarget.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RouteTargetResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RouteTargetFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RouteTargetFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RouteTargetFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
