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
import com.uniti.cil.itta.inventory.virtual.mapper.VnfMapper;
import com.uniti.cil.itta.inventory.virtual.model.Vnf;
import com.uniti.cil.itta.inventory.virtual.repository.VnfRepository;
import com.uniti.cil.itta.inventory.virtual.support.VnfFixtures;
import com.uniti.cil.itta.inventory.virtual.validation.VnfValidator;
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
class VnfServiceImplTest {

    @Mock
    private VnfRepository repository;
    @Mock
    private EventPublisher events;

    private VnfServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VnfFixtures.TENANT, "tester");
        service = new VnfServiceImpl(repository, new VnfMapper(), new VnfValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Vnf entity = VnfFixtures.entity();
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VnfFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VnfFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VnfFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VnfCreateRequest request = VnfFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VnfFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Vnf.class))).thenAnswer(inv -> inv.getArgument(0));
        VnfResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Vnf> captor = ArgumentCaptor.forClass(Vnf.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VnfFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VnfCreateRequest request = VnfFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VnfFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Vnf entity = VnfFixtures.entity();
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Vnf.class))).thenAnswer(inv -> inv.getArgument(0));
        VnfUpdateRequest request = new VnfUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VnfResponse response = service.update(VnfFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Vnf entity = VnfFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.of(entity));
        VnfUpdateRequest request = new VnfUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VnfFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Vnf entity = VnfFixtures.entity();
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VnfFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Vnf entity = VnfFixtures.entity();
        when(repository.findByTenantIdAndId(VnfFixtures.TENANT, VnfFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VnfFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VnfFixtures.entity())));
        assertThat(service.search(VnfFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VnfCreateRequest ok = VnfFixtures.createRequest();
        VnfCreateRequest dup = VnfFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VnfFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VnfFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Vnf.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VnfResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VnfFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VnfFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VnfFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
