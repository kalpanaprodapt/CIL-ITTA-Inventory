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
import com.uniti.cil.itta.inventory.service.mapper.ServiceOrderMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceOrder;
import com.uniti.cil.itta.inventory.service.repository.ServiceOrderRepository;
import com.uniti.cil.itta.inventory.service.support.ServiceOrderFixtures;
import com.uniti.cil.itta.inventory.service.validation.ServiceOrderValidator;
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
class ServiceOrderServiceImplTest {

    @Mock
    private ServiceOrderRepository repository;
    @Mock
    private EventPublisher events;

    private ServiceOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ServiceOrderFixtures.TENANT, "tester");
        service = new ServiceOrderServiceImpl(repository, new ServiceOrderMapper(), new ServiceOrderValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ServiceOrderFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ServiceOrderFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ServiceOrderFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ServiceOrderCreateRequest request = ServiceOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceOrderFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ServiceOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceOrderResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ServiceOrder> captor = ArgumentCaptor.forClass(ServiceOrder.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ServiceOrderFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ServiceOrderCreateRequest request = ServiceOrderFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceOrderFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServiceOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceOrderUpdateRequest request = new ServiceOrderUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ServiceOrderResponse response = service.update(ServiceOrderFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.of(entity));
        ServiceOrderUpdateRequest request = new ServiceOrderUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ServiceOrderFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ServiceOrderFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceOrderFixtures.TENANT, ServiceOrderFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ServiceOrderFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ServiceOrderFixtures.entity())));
        assertThat(service.search(ServiceOrderFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ServiceOrderCreateRequest ok = ServiceOrderFixtures.createRequest();
        ServiceOrderCreateRequest dup = ServiceOrderFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ServiceOrderFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ServiceOrderFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ServiceOrder.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ServiceOrderResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ServiceOrderFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ServiceOrderFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ServiceOrderFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
