package com.uniti.cil.itta.inventory.service.service;

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
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.mapper.ServiceInstanceMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceInstance;
import com.uniti.cil.itta.inventory.service.repository.ServiceInstanceRepository;
import com.uniti.cil.itta.inventory.service.support.ServiceInstanceFixtures;
import com.uniti.cil.itta.inventory.service.validation.ServiceInstanceValidator;
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
class ServiceInstanceServiceImplTest {

    @Mock
    private ServiceInstanceRepository repository;
    @Mock
    private EventPublisher events;

    private ServiceInstanceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ServiceInstanceFixtures.TENANT, "tester");
        service = new ServiceInstanceServiceImpl(repository, new ServiceInstanceMapper(), new ServiceInstanceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ServiceInstanceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ServiceInstanceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ServiceInstanceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ServiceInstanceCreateRequest request = ServiceInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceInstanceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ServiceInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceInstanceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ServiceInstance> captor = ArgumentCaptor.forClass(ServiceInstance.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ServiceInstanceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ServiceInstanceCreateRequest request = ServiceInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceInstanceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServiceInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceInstanceUpdateRequest request = new ServiceInstanceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ServiceInstanceResponse response = service.update(ServiceInstanceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        ServiceInstanceUpdateRequest request = new ServiceInstanceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ServiceInstanceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ServiceInstanceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceInstanceFixtures.TENANT, ServiceInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ServiceInstanceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ServiceInstanceFixtures.entity())));
        assertThat(service.search(ServiceInstanceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ServiceInstanceCreateRequest ok = ServiceInstanceFixtures.createRequest();
        ServiceInstanceCreateRequest dup = ServiceInstanceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ServiceInstanceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ServiceInstanceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ServiceInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ServiceInstanceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ServiceInstanceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ServiceInstanceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ServiceInstanceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
