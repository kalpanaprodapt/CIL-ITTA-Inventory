package com.uniti.cil.itta.inventory.cabling.service;

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
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.mapper.FiberStrandMapper;
import com.uniti.cil.itta.inventory.cabling.model.FiberStrand;
import com.uniti.cil.itta.inventory.cabling.repository.FiberStrandRepository;
import com.uniti.cil.itta.inventory.cabling.support.FiberStrandFixtures;
import com.uniti.cil.itta.inventory.cabling.validation.FiberStrandValidator;
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
class FiberStrandServiceImplTest {

    @Mock
    private FiberStrandRepository repository;
    @Mock
    private EventPublisher events;

    private FiberStrandServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(FiberStrandFixtures.TENANT, "tester");
        service = new FiberStrandServiceImpl(repository, new FiberStrandMapper(), new FiberStrandValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        FiberStrand entity = FiberStrandFixtures.entity();
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(FiberStrandFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(FiberStrandFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(FiberStrandFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        FiberStrandCreateRequest request = FiberStrandFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FiberStrandFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(FiberStrand.class))).thenAnswer(inv -> inv.getArgument(0));
        FiberStrandResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<FiberStrand> captor = ArgumentCaptor.forClass(FiberStrand.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(FiberStrandFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        FiberStrandCreateRequest request = FiberStrandFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FiberStrandFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        FiberStrand entity = FiberStrandFixtures.entity();
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(FiberStrand.class))).thenAnswer(inv -> inv.getArgument(0));
        FiberStrandUpdateRequest request = new FiberStrandUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        FiberStrandResponse response = service.update(FiberStrandFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        FiberStrand entity = FiberStrandFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.of(entity));
        FiberStrandUpdateRequest request = new FiberStrandUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(FiberStrandFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        FiberStrand entity = FiberStrandFixtures.entity();
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(FiberStrandFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        FiberStrand entity = FiberStrandFixtures.entity();
        when(repository.findByTenantIdAndId(FiberStrandFixtures.TENANT, FiberStrandFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(FiberStrandFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(FiberStrandFixtures.entity())));
        assertThat(service.search(FiberStrandFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        FiberStrandCreateRequest ok = FiberStrandFixtures.createRequest();
        FiberStrandCreateRequest dup = FiberStrandFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(FiberStrandFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(FiberStrandFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(FiberStrand.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<FiberStrandResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(FiberStrandFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(FiberStrandFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(FiberStrandFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
