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
import com.uniti.cil.itta.inventory.facility.mapper.RackMapper;
import com.uniti.cil.itta.inventory.facility.model.Rack;
import com.uniti.cil.itta.inventory.facility.repository.RackRepository;
import com.uniti.cil.itta.inventory.facility.support.RackFixtures;
import com.uniti.cil.itta.inventory.facility.validation.RackValidator;
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
class RackServiceImplTest {

    @Mock
    private RackRepository repository;
    @Mock
    private EventPublisher events;

    private RackServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RackFixtures.TENANT, "tester");
        service = new RackServiceImpl(repository, new RackMapper(), new RackValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Rack entity = RackFixtures.entity();
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RackFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RackFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RackFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RackCreateRequest request = RackFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RackFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Rack.class))).thenAnswer(inv -> inv.getArgument(0));
        RackResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Rack> captor = ArgumentCaptor.forClass(Rack.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RackFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RackCreateRequest request = RackFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RackFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Rack entity = RackFixtures.entity();
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Rack.class))).thenAnswer(inv -> inv.getArgument(0));
        RackUpdateRequest request = new RackUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RackResponse response = service.update(RackFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Rack entity = RackFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.of(entity));
        RackUpdateRequest request = new RackUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RackFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Rack entity = RackFixtures.entity();
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RackFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Rack entity = RackFixtures.entity();
        when(repository.findByTenantIdAndId(RackFixtures.TENANT, RackFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RackFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RackFixtures.entity())));
        assertThat(service.search(RackFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RackCreateRequest ok = RackFixtures.createRequest();
        RackCreateRequest dup = RackFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RackFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RackFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Rack.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RackResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RackFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RackFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RackFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
