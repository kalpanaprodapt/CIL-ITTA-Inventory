package com.uniti.cil.itta.inventory.vendor.service;

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
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.mapper.SupportContractMapper;
import com.uniti.cil.itta.inventory.vendor.model.SupportContract;
import com.uniti.cil.itta.inventory.vendor.repository.SupportContractRepository;
import com.uniti.cil.itta.inventory.vendor.support.SupportContractFixtures;
import com.uniti.cil.itta.inventory.vendor.validation.SupportContractValidator;
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
class SupportContractServiceImplTest {

    @Mock
    private SupportContractRepository repository;
    @Mock
    private EventPublisher events;

    private SupportContractServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SupportContractFixtures.TENANT, "tester");
        service = new SupportContractServiceImpl(repository, new SupportContractMapper(), new SupportContractValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SupportContract entity = SupportContractFixtures.entity();
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SupportContractFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SupportContractFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SupportContractFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SupportContractCreateRequest request = SupportContractFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SupportContractFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SupportContract.class))).thenAnswer(inv -> inv.getArgument(0));
        SupportContractResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SupportContract> captor = ArgumentCaptor.forClass(SupportContract.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SupportContractFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SupportContractCreateRequest request = SupportContractFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SupportContractFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SupportContract entity = SupportContractFixtures.entity();
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SupportContract.class))).thenAnswer(inv -> inv.getArgument(0));
        SupportContractUpdateRequest request = new SupportContractUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SupportContractResponse response = service.update(SupportContractFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SupportContract entity = SupportContractFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.of(entity));
        SupportContractUpdateRequest request = new SupportContractUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SupportContractFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SupportContract entity = SupportContractFixtures.entity();
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SupportContractFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SupportContract entity = SupportContractFixtures.entity();
        when(repository.findByTenantIdAndId(SupportContractFixtures.TENANT, SupportContractFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SupportContractFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SupportContractFixtures.entity())));
        assertThat(service.search(SupportContractFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SupportContractCreateRequest ok = SupportContractFixtures.createRequest();
        SupportContractCreateRequest dup = SupportContractFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SupportContractFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SupportContractFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SupportContract.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SupportContractResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SupportContractFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SupportContractFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SupportContractFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
