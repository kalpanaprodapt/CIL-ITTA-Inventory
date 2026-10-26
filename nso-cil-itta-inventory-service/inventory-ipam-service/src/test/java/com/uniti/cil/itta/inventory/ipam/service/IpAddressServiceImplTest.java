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
import com.uniti.cil.itta.inventory.ipam.mapper.IpAddressMapper;
import com.uniti.cil.itta.inventory.ipam.model.IpAddress;
import com.uniti.cil.itta.inventory.ipam.repository.IpAddressRepository;
import com.uniti.cil.itta.inventory.ipam.support.IpAddressFixtures;
import com.uniti.cil.itta.inventory.ipam.validation.IpAddressValidator;
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
class IpAddressServiceImplTest {

    @Mock
    private IpAddressRepository repository;
    @Mock
    private EventPublisher events;

    private IpAddressServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(IpAddressFixtures.TENANT, "tester");
        service = new IpAddressServiceImpl(repository, new IpAddressMapper(), new IpAddressValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        IpAddress entity = IpAddressFixtures.entity();
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(IpAddressFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(IpAddressFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(IpAddressFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        IpAddressCreateRequest request = IpAddressFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpAddressFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(IpAddress.class))).thenAnswer(inv -> inv.getArgument(0));
        IpAddressResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<IpAddress> captor = ArgumentCaptor.forClass(IpAddress.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(IpAddressFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        IpAddressCreateRequest request = IpAddressFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpAddressFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        IpAddress entity = IpAddressFixtures.entity();
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(IpAddress.class))).thenAnswer(inv -> inv.getArgument(0));
        IpAddressUpdateRequest request = new IpAddressUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        IpAddressResponse response = service.update(IpAddressFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        IpAddress entity = IpAddressFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.of(entity));
        IpAddressUpdateRequest request = new IpAddressUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(IpAddressFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        IpAddress entity = IpAddressFixtures.entity();
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(IpAddressFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        IpAddress entity = IpAddressFixtures.entity();
        when(repository.findByTenantIdAndId(IpAddressFixtures.TENANT, IpAddressFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(IpAddressFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(IpAddressFixtures.entity())));
        assertThat(service.search(IpAddressFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        IpAddressCreateRequest ok = IpAddressFixtures.createRequest();
        IpAddressCreateRequest dup = IpAddressFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(IpAddressFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(IpAddressFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(IpAddress.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<IpAddressResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(IpAddressFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(IpAddressFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(IpAddressFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
