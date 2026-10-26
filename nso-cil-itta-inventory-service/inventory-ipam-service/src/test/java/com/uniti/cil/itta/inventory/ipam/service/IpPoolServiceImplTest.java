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
import com.uniti.cil.itta.inventory.ipam.mapper.IpPoolMapper;
import com.uniti.cil.itta.inventory.ipam.model.IpPool;
import com.uniti.cil.itta.inventory.ipam.repository.IpPoolRepository;
import com.uniti.cil.itta.inventory.ipam.support.IpPoolFixtures;
import com.uniti.cil.itta.inventory.ipam.validation.IpPoolValidator;
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
class IpPoolServiceImplTest {

    @Mock
    private IpPoolRepository repository;
    @Mock
    private EventPublisher events;

    private IpPoolServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(IpPoolFixtures.TENANT, "tester");
        service = new IpPoolServiceImpl(repository, new IpPoolMapper(), new IpPoolValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        IpPool entity = IpPoolFixtures.entity();
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(IpPoolFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(IpPoolFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(IpPoolFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        IpPoolCreateRequest request = IpPoolFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpPoolFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(IpPool.class))).thenAnswer(inv -> inv.getArgument(0));
        IpPoolResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<IpPool> captor = ArgumentCaptor.forClass(IpPool.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(IpPoolFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        IpPoolCreateRequest request = IpPoolFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpPoolFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        IpPool entity = IpPoolFixtures.entity();
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(IpPool.class))).thenAnswer(inv -> inv.getArgument(0));
        IpPoolUpdateRequest request = new IpPoolUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        IpPoolResponse response = service.update(IpPoolFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        IpPool entity = IpPoolFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.of(entity));
        IpPoolUpdateRequest request = new IpPoolUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(IpPoolFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        IpPool entity = IpPoolFixtures.entity();
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(IpPoolFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        IpPool entity = IpPoolFixtures.entity();
        when(repository.findByTenantIdAndId(IpPoolFixtures.TENANT, IpPoolFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(IpPoolFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(IpPoolFixtures.entity())));
        assertThat(service.search(IpPoolFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        IpPoolCreateRequest ok = IpPoolFixtures.createRequest();
        IpPoolCreateRequest dup = IpPoolFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(IpPoolFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(IpPoolFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(IpPool.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<IpPoolResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(IpPoolFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(IpPoolFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(IpPoolFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
