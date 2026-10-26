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
import com.uniti.cil.itta.inventory.service.mapper.ServiceCatalogItemMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceCatalogItem;
import com.uniti.cil.itta.inventory.service.repository.ServiceCatalogItemRepository;
import com.uniti.cil.itta.inventory.service.support.ServiceCatalogItemFixtures;
import com.uniti.cil.itta.inventory.service.validation.ServiceCatalogItemValidator;
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
class ServiceCatalogItemServiceImplTest {

    @Mock
    private ServiceCatalogItemRepository repository;
    @Mock
    private EventPublisher events;

    private ServiceCatalogItemServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ServiceCatalogItemFixtures.TENANT, "tester");
        service = new ServiceCatalogItemServiceImpl(repository, new ServiceCatalogItemMapper(), new ServiceCatalogItemValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ServiceCatalogItemFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ServiceCatalogItemFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ServiceCatalogItemCreateRequest request = ServiceCatalogItemFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ServiceCatalogItem.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceCatalogItemResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ServiceCatalogItem> captor = ArgumentCaptor.forClass(ServiceCatalogItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ServiceCatalogItemFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ServiceCatalogItemCreateRequest request = ServiceCatalogItemFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServiceCatalogItem.class))).thenAnswer(inv -> inv.getArgument(0));
        ServiceCatalogItemUpdateRequest request = new ServiceCatalogItemUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ServiceCatalogItemResponse response = service.update(ServiceCatalogItemFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.of(entity));
        ServiceCatalogItemUpdateRequest request = new ServiceCatalogItemUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ServiceCatalogItemFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ServiceCatalogItemFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        when(repository.findByTenantIdAndId(ServiceCatalogItemFixtures.TENANT, ServiceCatalogItemFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ServiceCatalogItemFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ServiceCatalogItemFixtures.entity())));
        assertThat(service.search(ServiceCatalogItemFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ServiceCatalogItemCreateRequest ok = ServiceCatalogItemFixtures.createRequest();
        ServiceCatalogItemCreateRequest dup = ServiceCatalogItemFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ServiceCatalogItemFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ServiceCatalogItem.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ServiceCatalogItemResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ServiceCatalogItemFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ServiceCatalogItemFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ServiceCatalogItemFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
