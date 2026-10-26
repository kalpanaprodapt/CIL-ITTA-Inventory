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
import com.uniti.cil.itta.inventory.service.mapper.ServiceDependencyMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceDependency;
import com.uniti.cil.itta.inventory.service.repository.ServiceDependencyRepository;
import com.uniti.cil.itta.inventory.service.support.ServiceDependencyFixtures;
import com.uniti.cil.itta.inventory.service.validation.ServiceDependencyValidator;
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
class ServiceDependencyServiceImplTest {

    @Mock
    private ServiceDependencyRepository repository;
    @Mock
    private EventPublisher events;

    private ServiceDependencyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ServiceDependencyFixtures.TENANT, "tester");
        service = new ServiceDependencyServiceImpl(repository, new ServiceDependencyMapper(), new ServiceDependencyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ServiceDependencyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ServiceDependencyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ServiceDependencyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ServiceDependencyCreateRequest request = ServiceDependencyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceDependencyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ServiceDependency.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceDependencyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ServiceDependency> captor = ArgumentCaptor.forClass(ServiceDependency.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ServiceDependencyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ServiceDependencyCreateRequest request = ServiceDependencyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceDependencyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServiceDependency.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceDependencyUpdateRequest request = new ServiceDependencyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ServiceDependencyResponse response = service.update(ServiceDependencyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.of(entity));
        ServiceDependencyUpdateRequest request = new ServiceDependencyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ServiceDependencyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ServiceDependencyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceDependencyFixtures.TENANT, ServiceDependencyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ServiceDependencyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ServiceDependencyFixtures.entity())));
        assertThat(service.search(ServiceDependencyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ServiceDependencyCreateRequest ok = ServiceDependencyFixtures.createRequest();
        ServiceDependencyCreateRequest dup = ServiceDependencyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ServiceDependencyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ServiceDependencyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ServiceDependency.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ServiceDependencyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ServiceDependencyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ServiceDependencyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ServiceDependencyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
