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
import com.uniti.cil.itta.inventory.switching.mapper.VlanPoolMapper;
import com.uniti.cil.itta.inventory.switching.model.VlanPool;
import com.uniti.cil.itta.inventory.switching.repository.VlanPoolRepository;
import com.uniti.cil.itta.inventory.switching.support.VlanPoolFixtures;
import com.uniti.cil.itta.inventory.switching.validation.VlanPoolValidator;
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
class VlanPoolServiceImplTest {

    @Mock
    private VlanPoolRepository repository;
    @Mock
    private EventPublisher events;

    private VlanPoolServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VlanPoolFixtures.TENANT, "tester");
        service = new VlanPoolServiceImpl(repository, new VlanPoolMapper(), new VlanPoolValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        VlanPool entity = VlanPoolFixtures.entity();
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VlanPoolFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VlanPoolFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VlanPoolFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VlanPoolCreateRequest request = VlanPoolFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VlanPoolFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(VlanPool.class))).thenAnswer(inv -> inv.getArgument(0));
        VlanPoolResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<VlanPool> captor = ArgumentCaptor.forClass(VlanPool.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VlanPoolFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VlanPoolCreateRequest request = VlanPoolFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VlanPoolFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        VlanPool entity = VlanPoolFixtures.entity();
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(VlanPool.class))).thenAnswer(inv -> inv.getArgument(0));
        VlanPoolUpdateRequest request = new VlanPoolUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VlanPoolResponse response = service.update(VlanPoolFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        VlanPool entity = VlanPoolFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.of(entity));
        VlanPoolUpdateRequest request = new VlanPoolUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VlanPoolFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        VlanPool entity = VlanPoolFixtures.entity();
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VlanPoolFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        VlanPool entity = VlanPoolFixtures.entity();
        when(repository.findByTenantIdAndId(VlanPoolFixtures.TENANT, VlanPoolFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VlanPoolFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VlanPoolFixtures.entity())));
        assertThat(service.search(VlanPoolFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VlanPoolCreateRequest ok = VlanPoolFixtures.createRequest();
        VlanPoolCreateRequest dup = VlanPoolFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VlanPoolFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VlanPoolFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(VlanPool.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VlanPoolResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VlanPoolFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VlanPoolFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VlanPoolFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
