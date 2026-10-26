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
import com.uniti.cil.itta.inventory.vendor.mapper.VendorMapper;
import com.uniti.cil.itta.inventory.vendor.model.Vendor;
import com.uniti.cil.itta.inventory.vendor.repository.VendorRepository;
import com.uniti.cil.itta.inventory.vendor.support.VendorFixtures;
import com.uniti.cil.itta.inventory.vendor.validation.VendorValidator;
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
class VendorServiceImplTest {

    @Mock
    private VendorRepository repository;
    @Mock
    private EventPublisher events;

    private VendorServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VendorFixtures.TENANT, "tester");
        service = new VendorServiceImpl(repository, new VendorMapper(), new VendorValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Vendor entity = VendorFixtures.entity();
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VendorFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VendorFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VendorFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VendorCreateRequest request = VendorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VendorFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Vendor.class))).thenAnswer(inv -> inv.getArgument(0));
        VendorResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Vendor> captor = ArgumentCaptor.forClass(Vendor.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VendorFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VendorCreateRequest request = VendorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VendorFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Vendor entity = VendorFixtures.entity();
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Vendor.class))).thenAnswer(inv -> inv.getArgument(0));
        VendorUpdateRequest request = new VendorUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VendorResponse response = service.update(VendorFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Vendor entity = VendorFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.of(entity));
        VendorUpdateRequest request = new VendorUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VendorFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Vendor entity = VendorFixtures.entity();
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VendorFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Vendor entity = VendorFixtures.entity();
        when(repository.findByTenantIdAndId(VendorFixtures.TENANT, VendorFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VendorFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VendorFixtures.entity())));
        assertThat(service.search(VendorFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VendorCreateRequest ok = VendorFixtures.createRequest();
        VendorCreateRequest dup = VendorFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VendorFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VendorFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Vendor.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VendorResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VendorFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VendorFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VendorFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
