package com.uniti.cil.itta.inventory.ipam.service;

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
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.mapper.VrfInstanceMapper;
import com.uniti.cil.itta.inventory.ipam.model.VrfInstance;
import com.uniti.cil.itta.inventory.ipam.repository.VrfInstanceRepository;
import com.uniti.cil.itta.inventory.ipam.support.VrfInstanceFixtures;
import com.uniti.cil.itta.inventory.ipam.validation.VrfInstanceValidator;
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
class VrfInstanceServiceImplTest {

    @Mock
    private VrfInstanceRepository repository;
    @Mock
    private EventPublisher events;

    private VrfInstanceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VrfInstanceFixtures.TENANT, "tester");
        service = new VrfInstanceServiceImpl(repository, new VrfInstanceMapper(), new VrfInstanceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VrfInstanceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VrfInstanceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VrfInstanceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VrfInstanceCreateRequest request = VrfInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VrfInstanceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(VrfInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        VrfInstanceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<VrfInstance> captor = ArgumentCaptor.forClass(VrfInstance.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VrfInstanceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VrfInstanceCreateRequest request = VrfInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VrfInstanceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(VrfInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        VrfInstanceUpdateRequest request = new VrfInstanceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VrfInstanceResponse response = service.update(VrfInstanceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        VrfInstanceUpdateRequest request = new VrfInstanceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VrfInstanceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VrfInstanceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(VrfInstanceFixtures.TENANT, VrfInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VrfInstanceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VrfInstanceFixtures.entity())));
        assertThat(service.search(VrfInstanceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VrfInstanceCreateRequest ok = VrfInstanceFixtures.createRequest();
        VrfInstanceCreateRequest dup = VrfInstanceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VrfInstanceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VrfInstanceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(VrfInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VrfInstanceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VrfInstanceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VrfInstanceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VrfInstanceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
