package com.uniti.cil.itta.inventory.device.service;

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
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.mapper.NetworkDeviceMapper;
import com.uniti.cil.itta.inventory.device.model.NetworkDevice;
import com.uniti.cil.itta.inventory.device.repository.NetworkDeviceRepository;
import com.uniti.cil.itta.inventory.device.support.NetworkDeviceFixtures;
import com.uniti.cil.itta.inventory.device.validation.NetworkDeviceValidator;
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
class NetworkDeviceServiceImplTest {

    @Mock
    private NetworkDeviceRepository repository;
    @Mock
    private EventPublisher events;

    private NetworkDeviceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(NetworkDeviceFixtures.TENANT, "tester");
        service = new NetworkDeviceServiceImpl(repository, new NetworkDeviceMapper(), new NetworkDeviceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(NetworkDeviceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(NetworkDeviceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(NetworkDeviceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        NetworkDeviceCreateRequest request = NetworkDeviceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(NetworkDeviceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(NetworkDevice.class))).thenAnswer(inv -> inv.getArgument(0));
        NetworkDeviceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<NetworkDevice> captor = ArgumentCaptor.forClass(NetworkDevice.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(NetworkDeviceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        NetworkDeviceCreateRequest request = NetworkDeviceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(NetworkDeviceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(NetworkDevice.class))).thenAnswer(inv -> inv.getArgument(0));
        NetworkDeviceUpdateRequest request = new NetworkDeviceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        NetworkDeviceResponse response = service.update(NetworkDeviceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.of(entity));
        NetworkDeviceUpdateRequest request = new NetworkDeviceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(NetworkDeviceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(NetworkDeviceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        when(repository.findByTenantIdAndId(NetworkDeviceFixtures.TENANT, NetworkDeviceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(NetworkDeviceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(NetworkDeviceFixtures.entity())));
        assertThat(service.search(NetworkDeviceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        NetworkDeviceCreateRequest ok = NetworkDeviceFixtures.createRequest();
        NetworkDeviceCreateRequest dup = NetworkDeviceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(NetworkDeviceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(NetworkDeviceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(NetworkDevice.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<NetworkDeviceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(NetworkDeviceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(NetworkDeviceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(NetworkDeviceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
