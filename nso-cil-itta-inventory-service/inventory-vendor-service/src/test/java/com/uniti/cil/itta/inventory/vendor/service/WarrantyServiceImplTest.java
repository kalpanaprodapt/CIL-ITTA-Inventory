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
import com.uniti.cil.itta.inventory.vendor.mapper.WarrantyMapper;
import com.uniti.cil.itta.inventory.vendor.model.Warranty;
import com.uniti.cil.itta.inventory.vendor.repository.WarrantyRepository;
import com.uniti.cil.itta.inventory.vendor.support.WarrantyFixtures;
import com.uniti.cil.itta.inventory.vendor.validation.WarrantyValidator;
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
class WarrantyServiceImplTest {

    @Mock
    private WarrantyRepository repository;
    @Mock
    private EventPublisher events;

    private WarrantyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(WarrantyFixtures.TENANT, "tester");
        service = new WarrantyServiceImpl(repository, new WarrantyMapper(), new WarrantyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Warranty entity = WarrantyFixtures.entity();
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(WarrantyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(WarrantyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(WarrantyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        WarrantyCreateRequest request = WarrantyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WarrantyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Warranty.class))).thenAnswer(inv -> inv.getArgument(0));
        WarrantyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Warranty> captor = ArgumentCaptor.forClass(Warranty.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(WarrantyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        WarrantyCreateRequest request = WarrantyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WarrantyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Warranty entity = WarrantyFixtures.entity();
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Warranty.class))).thenAnswer(inv -> inv.getArgument(0));
        WarrantyUpdateRequest request = new WarrantyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        WarrantyResponse response = service.update(WarrantyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Warranty entity = WarrantyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.of(entity));
        WarrantyUpdateRequest request = new WarrantyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(WarrantyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Warranty entity = WarrantyFixtures.entity();
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(WarrantyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Warranty entity = WarrantyFixtures.entity();
        when(repository.findByTenantIdAndId(WarrantyFixtures.TENANT, WarrantyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(WarrantyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(WarrantyFixtures.entity())));
        assertThat(service.search(WarrantyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        WarrantyCreateRequest ok = WarrantyFixtures.createRequest();
        WarrantyCreateRequest dup = WarrantyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(WarrantyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(WarrantyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Warranty.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<WarrantyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(WarrantyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(WarrantyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(WarrantyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
