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
import com.uniti.cil.itta.inventory.virtual.mapper.HypervisorMapper;
import com.uniti.cil.itta.inventory.virtual.model.Hypervisor;
import com.uniti.cil.itta.inventory.virtual.repository.HypervisorRepository;
import com.uniti.cil.itta.inventory.virtual.support.HypervisorFixtures;
import com.uniti.cil.itta.inventory.virtual.validation.HypervisorValidator;
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
class HypervisorServiceImplTest {

    @Mock
    private HypervisorRepository repository;
    @Mock
    private EventPublisher events;

    private HypervisorServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(HypervisorFixtures.TENANT, "tester");
        service = new HypervisorServiceImpl(repository, new HypervisorMapper(), new HypervisorValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Hypervisor entity = HypervisorFixtures.entity();
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(HypervisorFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(HypervisorFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(HypervisorFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        HypervisorCreateRequest request = HypervisorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(HypervisorFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Hypervisor.class))).thenAnswer(inv -> inv.getArgument(0));
        HypervisorResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Hypervisor> captor = ArgumentCaptor.forClass(Hypervisor.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(HypervisorFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        HypervisorCreateRequest request = HypervisorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(HypervisorFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Hypervisor entity = HypervisorFixtures.entity();
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Hypervisor.class))).thenAnswer(inv -> inv.getArgument(0));
        HypervisorUpdateRequest request = new HypervisorUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        HypervisorResponse response = service.update(HypervisorFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Hypervisor entity = HypervisorFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.of(entity));
        HypervisorUpdateRequest request = new HypervisorUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(HypervisorFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Hypervisor entity = HypervisorFixtures.entity();
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(HypervisorFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Hypervisor entity = HypervisorFixtures.entity();
        when(repository.findByTenantIdAndId(HypervisorFixtures.TENANT, HypervisorFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(HypervisorFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(HypervisorFixtures.entity())));
        assertThat(service.search(HypervisorFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        HypervisorCreateRequest ok = HypervisorFixtures.createRequest();
        HypervisorCreateRequest dup = HypervisorFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(HypervisorFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(HypervisorFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Hypervisor.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<HypervisorResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(HypervisorFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(HypervisorFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(HypervisorFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
