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
import com.uniti.cil.itta.inventory.customer.mapper.CustomerAccountMapper;
import com.uniti.cil.itta.inventory.customer.model.CustomerAccount;
import com.uniti.cil.itta.inventory.customer.repository.CustomerAccountRepository;
import com.uniti.cil.itta.inventory.customer.support.CustomerAccountFixtures;
import com.uniti.cil.itta.inventory.customer.validation.CustomerAccountValidator;
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
class CustomerAccountServiceImplTest {

    @Mock
    private CustomerAccountRepository repository;
    @Mock
    private EventPublisher events;

    private CustomerAccountServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CustomerAccountFixtures.TENANT, "tester");
        service = new CustomerAccountServiceImpl(repository, new CustomerAccountMapper(), new CustomerAccountValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CustomerAccountFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CustomerAccountFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CustomerAccountFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CustomerAccountCreateRequest request = CustomerAccountFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerAccountFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CustomerAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerAccountResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CustomerAccount> captor = ArgumentCaptor.forClass(CustomerAccount.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CustomerAccountFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CustomerAccountCreateRequest request = CustomerAccountFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CustomerAccountFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CustomerAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        CustomerAccountUpdateRequest request = new CustomerAccountUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CustomerAccountResponse response = service.update(CustomerAccountFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.of(entity));
        CustomerAccountUpdateRequest request = new CustomerAccountUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CustomerAccountFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CustomerAccountFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CustomerAccountFixtures.TENANT, CustomerAccountFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CustomerAccountFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CustomerAccountFixtures.entity())));
        assertThat(service.search(CustomerAccountFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CustomerAccountCreateRequest ok = CustomerAccountFixtures.createRequest();
        CustomerAccountCreateRequest dup = CustomerAccountFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CustomerAccountFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CustomerAccountFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CustomerAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CustomerAccountResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CustomerAccountFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CustomerAccountFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CustomerAccountFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
