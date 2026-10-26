package com.uniti.cil.itta.inventory.integration.service;

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
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.mapper.ImportBatchMapper;
import com.uniti.cil.itta.inventory.integration.model.ImportBatch;
import com.uniti.cil.itta.inventory.integration.repository.ImportBatchRepository;
import com.uniti.cil.itta.inventory.integration.support.ImportBatchFixtures;
import com.uniti.cil.itta.inventory.integration.validation.ImportBatchValidator;
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
class ImportBatchServiceImplTest {

    @Mock
    private ImportBatchRepository repository;
    @Mock
    private EventPublisher events;

    private ImportBatchServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ImportBatchFixtures.TENANT, "tester");
        service = new ImportBatchServiceImpl(repository, new ImportBatchMapper(), new ImportBatchValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ImportBatch entity = ImportBatchFixtures.entity();
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ImportBatchFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ImportBatchFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ImportBatchFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ImportBatchCreateRequest request = ImportBatchFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ImportBatchFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ImportBatch.class))).thenAnswer(inv -> inv.getArgument(0));
        ImportBatchResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ImportBatch> captor = ArgumentCaptor.forClass(ImportBatch.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ImportBatchFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ImportBatchCreateRequest request = ImportBatchFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ImportBatchFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ImportBatch entity = ImportBatchFixtures.entity();
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ImportBatch.class))).thenAnswer(inv -> inv.getArgument(0));
        ImportBatchUpdateRequest request = new ImportBatchUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ImportBatchResponse response = service.update(ImportBatchFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ImportBatch entity = ImportBatchFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.of(entity));
        ImportBatchUpdateRequest request = new ImportBatchUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ImportBatchFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ImportBatch entity = ImportBatchFixtures.entity();
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ImportBatchFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ImportBatch entity = ImportBatchFixtures.entity();
        when(repository.findByTenantIdAndId(ImportBatchFixtures.TENANT, ImportBatchFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ImportBatchFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ImportBatchFixtures.entity())));
        assertThat(service.search(ImportBatchFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ImportBatchCreateRequest ok = ImportBatchFixtures.createRequest();
        ImportBatchCreateRequest dup = ImportBatchFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ImportBatchFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ImportBatchFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ImportBatch.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ImportBatchResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ImportBatchFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ImportBatchFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ImportBatchFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
