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
import com.uniti.cil.itta.inventory.cabling.mapper.SpliceMapper;
import com.uniti.cil.itta.inventory.cabling.model.Splice;
import com.uniti.cil.itta.inventory.cabling.repository.SpliceRepository;
import com.uniti.cil.itta.inventory.cabling.support.SpliceFixtures;
import com.uniti.cil.itta.inventory.cabling.validation.SpliceValidator;
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
class SpliceServiceImplTest {

    @Mock
    private SpliceRepository repository;
    @Mock
    private EventPublisher events;

    private SpliceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SpliceFixtures.TENANT, "tester");
        service = new SpliceServiceImpl(repository, new SpliceMapper(), new SpliceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Splice entity = SpliceFixtures.entity();
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SpliceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SpliceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SpliceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SpliceCreateRequest request = SpliceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SpliceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Splice.class))).thenAnswer(inv -> inv.getArgument(0));
        SpliceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Splice> captor = ArgumentCaptor.forClass(Splice.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SpliceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SpliceCreateRequest request = SpliceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SpliceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Splice entity = SpliceFixtures.entity();
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Splice.class))).thenAnswer(inv -> inv.getArgument(0));
        SpliceUpdateRequest request = new SpliceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SpliceResponse response = service.update(SpliceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Splice entity = SpliceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.of(entity));
        SpliceUpdateRequest request = new SpliceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SpliceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Splice entity = SpliceFixtures.entity();
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SpliceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Splice entity = SpliceFixtures.entity();
        when(repository.findByTenantIdAndId(SpliceFixtures.TENANT, SpliceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SpliceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SpliceFixtures.entity())));
        assertThat(service.search(SpliceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SpliceCreateRequest ok = SpliceFixtures.createRequest();
        SpliceCreateRequest dup = SpliceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SpliceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SpliceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Splice.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SpliceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SpliceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SpliceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SpliceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
