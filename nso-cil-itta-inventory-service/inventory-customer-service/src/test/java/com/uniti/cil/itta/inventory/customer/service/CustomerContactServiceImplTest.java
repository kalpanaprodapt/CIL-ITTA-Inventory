package com.uniti.cil.itta.inventory.customer.service;

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
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.mapper.CustomerContactMapper;
import com.uniti.cil.itta.inventory.customer.model.CustomerContact;
import com.uniti.cil.itta.inventory.customer.repository.CustomerContactRepository;
import com.uniti.cil.itta.inventory.customer.support.CustomerContactFixtures;
import com.uniti.cil.itta.inventory.customer.validation.CustomerContactValidator;
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
class CustomerContactServiceImplTest {

    @Mock
    private CustomerContactRepository repository;
    @Mock
    private EventPublisher events;

    private CustomerContactServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CustomerContactFixtures.TENANT, "tester");
        service = new CustomerContactServiceImpl(repository, new CustomerContactMapper(), new CustomerContactValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CustomerContact entity = CustomerContactFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CustomerContactFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CustomerContactFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CustomerContactFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CustomerContactCreateRequest request = CustomerContactFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerContactFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CustomerContact.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerContactResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CustomerContact> captor = ArgumentCaptor.forClass(CustomerContact.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CustomerContactFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CustomerContactCreateRequest request = CustomerContactFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerContactFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CustomerContact entity = CustomerContactFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CustomerContact.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerContactUpdateRequest request = new CustomerContactUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CustomerContactResponse response = service.update(CustomerContactFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CustomerContact entity = CustomerContactFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.of(entity));
        CustomerContactUpdateRequest request = new CustomerContactUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CustomerContactFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CustomerContact entity = CustomerContactFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CustomerContactFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CustomerContact entity = CustomerContactFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerContactFixtures.TENANT, CustomerContactFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CustomerContactFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CustomerContactFixtures.entity())));
        assertThat(service.search(CustomerContactFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CustomerContactCreateRequest ok = CustomerContactFixtures.createRequest();
        CustomerContactCreateRequest dup = CustomerContactFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CustomerContactFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CustomerContactFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CustomerContact.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CustomerContactResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CustomerContactFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CustomerContactFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CustomerContactFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
