package com.uniti.cil.itta.inventory.mpls.service;

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
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.mapper.MplsLspMapper;
import com.uniti.cil.itta.inventory.mpls.model.MplsLsp;
import com.uniti.cil.itta.inventory.mpls.repository.MplsLspRepository;
import com.uniti.cil.itta.inventory.mpls.support.MplsLspFixtures;
import com.uniti.cil.itta.inventory.mpls.validation.MplsLspValidator;
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
class MplsLspServiceImplTest {

    @Mock
    private MplsLspRepository repository;
    @Mock
    private EventPublisher events;

    private MplsLspServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(MplsLspFixtures.TENANT, "tester");
        service = new MplsLspServiceImpl(repository, new MplsLspMapper(), new MplsLspValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        MplsLsp entity = MplsLspFixtures.entity();
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(MplsLspFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(MplsLspFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(MplsLspFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        MplsLspCreateRequest request = MplsLspFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(MplsLspFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(MplsLsp.class))).thenAnswer(inv -> inv.getArgument(0));
        MplsLspResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<MplsLsp> captor = ArgumentCaptor.forClass(MplsLsp.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(MplsLspFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        MplsLspCreateRequest request = MplsLspFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(MplsLspFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        MplsLsp entity = MplsLspFixtures.entity();
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(MplsLsp.class))).thenAnswer(inv -> inv.getArgument(0));
        MplsLspUpdateRequest request = new MplsLspUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        MplsLspResponse response = service.update(MplsLspFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        MplsLsp entity = MplsLspFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.of(entity));
        MplsLspUpdateRequest request = new MplsLspUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(MplsLspFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        MplsLsp entity = MplsLspFixtures.entity();
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(MplsLspFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        MplsLsp entity = MplsLspFixtures.entity();
        when(repository.findByTenantIdAndId(MplsLspFixtures.TENANT, MplsLspFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(MplsLspFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(MplsLspFixtures.entity())));
        assertThat(service.search(MplsLspFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        MplsLspCreateRequest ok = MplsLspFixtures.createRequest();
        MplsLspCreateRequest dup = MplsLspFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(MplsLspFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(MplsLspFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(MplsLsp.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<MplsLspResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(MplsLspFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(MplsLspFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(MplsLspFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
