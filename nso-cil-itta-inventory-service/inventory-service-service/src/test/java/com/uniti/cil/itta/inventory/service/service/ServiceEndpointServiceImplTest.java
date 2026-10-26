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
import com.uniti.cil.itta.inventory.service.mapper.ServiceEndpointMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceEndpoint;
import com.uniti.cil.itta.inventory.service.repository.ServiceEndpointRepository;
import com.uniti.cil.itta.inventory.service.support.ServiceEndpointFixtures;
import com.uniti.cil.itta.inventory.service.validation.ServiceEndpointValidator;
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
class ServiceEndpointServiceImplTest {

    @Mock
    private ServiceEndpointRepository repository;
    @Mock
    private EventPublisher events;

    private ServiceEndpointServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ServiceEndpointFixtures.TENANT, "tester");
        service = new ServiceEndpointServiceImpl(repository, new ServiceEndpointMapper(), new ServiceEndpointValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ServiceEndpointFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ServiceEndpointFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ServiceEndpointFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ServiceEndpointCreateRequest request = ServiceEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceEndpointFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ServiceEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceEndpointResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ServiceEndpoint> captor = ArgumentCaptor.forClass(ServiceEndpoint.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ServiceEndpointFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ServiceEndpointCreateRequest request = ServiceEndpointFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceEndpointFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServiceEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceEndpointUpdateRequest request = new ServiceEndpointUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ServiceEndpointResponse response = service.update(ServiceEndpointFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        ServiceEndpointUpdateRequest request = new ServiceEndpointUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ServiceEndpointFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ServiceEndpointFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceEndpointFixtures.TENANT, ServiceEndpointFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ServiceEndpointFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ServiceEndpointFixtures.entity())));
        assertThat(service.search(ServiceEndpointFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ServiceEndpointCreateRequest ok = ServiceEndpointFixtures.createRequest();
        ServiceEndpointCreateRequest dup = ServiceEndpointFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ServiceEndpointFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ServiceEndpointFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ServiceEndpoint.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ServiceEndpointResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ServiceEndpointFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ServiceEndpointFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ServiceEndpointFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
