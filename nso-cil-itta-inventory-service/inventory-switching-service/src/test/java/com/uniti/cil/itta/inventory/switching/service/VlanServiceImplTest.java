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
import com.uniti.cil.itta.inventory.switching.mapper.VlanMapper;
import com.uniti.cil.itta.inventory.switching.model.Vlan;
import com.uniti.cil.itta.inventory.switching.repository.VlanRepository;
import com.uniti.cil.itta.inventory.switching.support.VlanFixtures;
import com.uniti.cil.itta.inventory.switching.validation.VlanValidator;
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
class VlanServiceImplTest {

    @Mock
    private VlanRepository repository;
    @Mock
    private EventPublisher events;

    private VlanServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VlanFixtures.TENANT, "tester");
        service = new VlanServiceImpl(repository, new VlanMapper(), new VlanValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Vlan entity = VlanFixtures.entity();
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VlanFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VlanFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VlanFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VlanCreateRequest request = VlanFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VlanFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Vlan.class))).thenAnswer(inv -> inv.getArgument(0));
        VlanResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Vlan> captor = ArgumentCaptor.forClass(Vlan.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VlanFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VlanCreateRequest request = VlanFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VlanFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Vlan entity = VlanFixtures.entity();
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Vlan.class))).thenAnswer(inv -> inv.getArgument(0));
        VlanUpdateRequest request = new VlanUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VlanResponse response = service.update(VlanFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Vlan entity = VlanFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.of(entity));
        VlanUpdateRequest request = new VlanUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VlanFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Vlan entity = VlanFixtures.entity();
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VlanFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Vlan entity = VlanFixtures.entity();
        when(repository.findByTenantIdAndId(VlanFixtures.TENANT, VlanFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VlanFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VlanFixtures.entity())));
        assertThat(service.search(VlanFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VlanCreateRequest ok = VlanFixtures.createRequest();
        VlanCreateRequest dup = VlanFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VlanFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VlanFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Vlan.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VlanResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VlanFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VlanFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VlanFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
