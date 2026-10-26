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
import com.uniti.cil.itta.inventory.customer.mapper.CustomerMapper;
import com.uniti.cil.itta.inventory.customer.model.Customer;
import com.uniti.cil.itta.inventory.customer.repository.CustomerRepository;
import com.uniti.cil.itta.inventory.customer.support.CustomerFixtures;
import com.uniti.cil.itta.inventory.customer.validation.CustomerValidator;
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
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;
    @Mock
    private EventPublisher events;

    private CustomerServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CustomerFixtures.TENANT, "tester");
        service = new CustomerServiceImpl(repository, new CustomerMapper(), new CustomerValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Customer entity = CustomerFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CustomerFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CustomerFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CustomerFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CustomerCreateRequest request = CustomerFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CustomerFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CustomerCreateRequest request = CustomerFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Customer entity = CustomerFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CustomerResponse response = service.update(CustomerFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Customer entity = CustomerFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.of(entity));
        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CustomerFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Customer entity = CustomerFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CustomerFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Customer entity = CustomerFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerFixtures.TENANT, CustomerFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CustomerFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CustomerFixtures.entity())));
        assertThat(service.search(CustomerFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CustomerCreateRequest ok = CustomerFixtures.createRequest();
        CustomerCreateRequest dup = CustomerFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CustomerFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CustomerFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CustomerResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CustomerFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CustomerFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CustomerFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
