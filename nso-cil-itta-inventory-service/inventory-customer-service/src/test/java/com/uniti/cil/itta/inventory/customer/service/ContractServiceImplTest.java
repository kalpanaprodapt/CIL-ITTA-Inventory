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
import com.uniti.cil.itta.inventory.customer.mapper.ContractMapper;
import com.uniti.cil.itta.inventory.customer.model.Contract;
import com.uniti.cil.itta.inventory.customer.repository.ContractRepository;
import com.uniti.cil.itta.inventory.customer.support.ContractFixtures;
import com.uniti.cil.itta.inventory.customer.validation.ContractValidator;
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
class ContractServiceImplTest {

    @Mock
    private ContractRepository repository;
    @Mock
    private EventPublisher events;

    private ContractServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ContractFixtures.TENANT, "tester");
        service = new ContractServiceImpl(repository, new ContractMapper(), new ContractValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Contract entity = ContractFixtures.entity();
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ContractFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ContractFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ContractFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ContractCreateRequest request = ContractFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ContractFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Contract.class))).thenAnswer(inv -> inv.getArgument(0));
        ContractResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Contract> captor = ArgumentCaptor.forClass(Contract.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ContractFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ContractCreateRequest request = ContractFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ContractFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Contract entity = ContractFixtures.entity();
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Contract.class))).thenAnswer(inv -> inv.getArgument(0));
        ContractUpdateRequest request = new ContractUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ContractResponse response = service.update(ContractFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Contract entity = ContractFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.of(entity));
        ContractUpdateRequest request = new ContractUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ContractFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Contract entity = ContractFixtures.entity();
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ContractFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Contract entity = ContractFixtures.entity();
        when(repository.findByTenantIdAndId(ContractFixtures.TENANT, ContractFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ContractFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ContractFixtures.entity())));
        assertThat(service.search(ContractFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ContractCreateRequest ok = ContractFixtures.createRequest();
        ContractCreateRequest dup = ContractFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ContractFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ContractFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Contract.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ContractResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ContractFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ContractFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ContractFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
