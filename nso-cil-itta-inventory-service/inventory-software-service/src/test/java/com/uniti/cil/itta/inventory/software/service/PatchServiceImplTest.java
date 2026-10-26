package com.uniti.cil.itta.inventory.software.service;

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
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.mapper.PatchMapper;
import com.uniti.cil.itta.inventory.software.model.Patch;
import com.uniti.cil.itta.inventory.software.repository.PatchRepository;
import com.uniti.cil.itta.inventory.software.support.PatchFixtures;
import com.uniti.cil.itta.inventory.software.validation.PatchValidator;
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
class PatchServiceImplTest {

    @Mock
    private PatchRepository repository;
    @Mock
    private EventPublisher events;

    private PatchServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PatchFixtures.TENANT, "tester");
        service = new PatchServiceImpl(repository, new PatchMapper(), new PatchValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Patch entity = PatchFixtures.entity();
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PatchFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PatchFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PatchFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PatchCreateRequest request = PatchFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Patch.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Patch> captor = ArgumentCaptor.forClass(Patch.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PatchFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PatchCreateRequest request = PatchFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Patch entity = PatchFixtures.entity();
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Patch.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchUpdateRequest request = new PatchUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PatchResponse response = service.update(PatchFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Patch entity = PatchFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.of(entity));
        PatchUpdateRequest request = new PatchUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PatchFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Patch entity = PatchFixtures.entity();
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PatchFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Patch entity = PatchFixtures.entity();
        when(repository.findByTenantIdAndId(PatchFixtures.TENANT, PatchFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PatchFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PatchFixtures.entity())));
        assertThat(service.search(PatchFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PatchCreateRequest ok = PatchFixtures.createRequest();
        PatchCreateRequest dup = PatchFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PatchFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PatchFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Patch.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PatchResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PatchFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PatchFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PatchFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
