package com.uniti.cil.itta.inventory.virtual.service;

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
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.mapper.VirtualMachineMapper;
import com.uniti.cil.itta.inventory.virtual.model.VirtualMachine;
import com.uniti.cil.itta.inventory.virtual.repository.VirtualMachineRepository;
import com.uniti.cil.itta.inventory.virtual.support.VirtualMachineFixtures;
import com.uniti.cil.itta.inventory.virtual.validation.VirtualMachineValidator;
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
class VirtualMachineServiceImplTest {

    @Mock
    private VirtualMachineRepository repository;
    @Mock
    private EventPublisher events;

    private VirtualMachineServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VirtualMachineFixtures.TENANT, "tester");
        service = new VirtualMachineServiceImpl(repository, new VirtualMachineMapper(), new VirtualMachineValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VirtualMachineFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VirtualMachineFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VirtualMachineFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VirtualMachineCreateRequest request = VirtualMachineFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VirtualMachineFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(VirtualMachine.class))).thenAnswer(inv -> inv.getArgument(0));
        VirtualMachineResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<VirtualMachine> captor = ArgumentCaptor.forClass(VirtualMachine.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VirtualMachineFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VirtualMachineCreateRequest request = VirtualMachineFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VirtualMachineFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(VirtualMachine.class))).thenAnswer(inv -> inv.getArgument(0));
        VirtualMachineUpdateRequest request = new VirtualMachineUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VirtualMachineResponse response = service.update(VirtualMachineFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.of(entity));
        VirtualMachineUpdateRequest request = new VirtualMachineUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VirtualMachineFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VirtualMachineFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        when(repository.findByTenantIdAndId(VirtualMachineFixtures.TENANT, VirtualMachineFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VirtualMachineFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VirtualMachineFixtures.entity())));
        assertThat(service.search(VirtualMachineFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VirtualMachineCreateRequest ok = VirtualMachineFixtures.createRequest();
        VirtualMachineCreateRequest dup = VirtualMachineFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VirtualMachineFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VirtualMachineFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(VirtualMachine.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VirtualMachineResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VirtualMachineFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VirtualMachineFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VirtualMachineFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
