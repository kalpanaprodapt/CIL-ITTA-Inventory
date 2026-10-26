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
import com.uniti.cil.itta.inventory.ipam.mapper.IpSubnetMapper;
import com.uniti.cil.itta.inventory.ipam.model.IpSubnet;
import com.uniti.cil.itta.inventory.ipam.repository.IpSubnetRepository;
import com.uniti.cil.itta.inventory.ipam.support.IpSubnetFixtures;
import com.uniti.cil.itta.inventory.ipam.validation.IpSubnetValidator;
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
class IpSubnetServiceImplTest {

    @Mock
    private IpSubnetRepository repository;
    @Mock
    private EventPublisher events;

    private IpSubnetServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(IpSubnetFixtures.TENANT, "tester");
        service = new IpSubnetServiceImpl(repository, new IpSubnetMapper(), new IpSubnetValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        IpSubnet entity = IpSubnetFixtures.entity();
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(IpSubnetFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(IpSubnetFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(IpSubnetFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        IpSubnetCreateRequest request = IpSubnetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpSubnetFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(IpSubnet.class))).thenAnswer(inv -> inv.getArgument(0));
        IpSubnetResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<IpSubnet> captor = ArgumentCaptor.forClass(IpSubnet.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(IpSubnetFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        IpSubnetCreateRequest request = IpSubnetFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(IpSubnetFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        IpSubnet entity = IpSubnetFixtures.entity();
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(IpSubnet.class))).thenAnswer(inv -> inv.getArgument(0));
        IpSubnetUpdateRequest request = new IpSubnetUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        IpSubnetResponse response = service.update(IpSubnetFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        IpSubnet entity = IpSubnetFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.of(entity));
        IpSubnetUpdateRequest request = new IpSubnetUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(IpSubnetFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        IpSubnet entity = IpSubnetFixtures.entity();
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(IpSubnetFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        IpSubnet entity = IpSubnetFixtures.entity();
        when(repository.findByTenantIdAndId(IpSubnetFixtures.TENANT, IpSubnetFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(IpSubnetFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(IpSubnetFixtures.entity())));
        assertThat(service.search(IpSubnetFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        IpSubnetCreateRequest ok = IpSubnetFixtures.createRequest();
        IpSubnetCreateRequest dup = IpSubnetFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(IpSubnetFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(IpSubnetFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(IpSubnet.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<IpSubnetResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(IpSubnetFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(IpSubnetFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(IpSubnetFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
