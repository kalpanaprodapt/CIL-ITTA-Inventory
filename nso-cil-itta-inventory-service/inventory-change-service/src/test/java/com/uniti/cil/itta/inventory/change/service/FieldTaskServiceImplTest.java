package com.uniti.cil.itta.inventory.change.service;

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
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.mapper.FieldTaskMapper;
import com.uniti.cil.itta.inventory.change.model.FieldTask;
import com.uniti.cil.itta.inventory.change.repository.FieldTaskRepository;
import com.uniti.cil.itta.inventory.change.support.FieldTaskFixtures;
import com.uniti.cil.itta.inventory.change.validation.FieldTaskValidator;
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
class FieldTaskServiceImplTest {

    @Mock
    private FieldTaskRepository repository;
    @Mock
    private EventPublisher events;

    private FieldTaskServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(FieldTaskFixtures.TENANT, "tester");
        service = new FieldTaskServiceImpl(repository, new FieldTaskMapper(), new FieldTaskValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        FieldTask entity = FieldTaskFixtures.entity();
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(FieldTaskFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(FieldTaskFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(FieldTaskFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        FieldTaskCreateRequest request = FieldTaskFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FieldTaskFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(FieldTask.class))).thenAnswer(inv -> inv.getArgument(0));
        FieldTaskResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<FieldTask> captor = ArgumentCaptor.forClass(FieldTask.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(FieldTaskFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        FieldTaskCreateRequest request = FieldTaskFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FieldTaskFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        FieldTask entity = FieldTaskFixtures.entity();
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(FieldTask.class))).thenAnswer(inv -> inv.getArgument(0));
        FieldTaskUpdateRequest request = new FieldTaskUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        FieldTaskResponse response = service.update(FieldTaskFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        FieldTask entity = FieldTaskFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.of(entity));
        FieldTaskUpdateRequest request = new FieldTaskUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(FieldTaskFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        FieldTask entity = FieldTaskFixtures.entity();
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(FieldTaskFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        FieldTask entity = FieldTaskFixtures.entity();
        when(repository.findByTenantIdAndId(FieldTaskFixtures.TENANT, FieldTaskFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(FieldTaskFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(FieldTaskFixtures.entity())));
        assertThat(service.search(FieldTaskFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        FieldTaskCreateRequest ok = FieldTaskFixtures.createRequest();
        FieldTaskCreateRequest dup = FieldTaskFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(FieldTaskFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(FieldTaskFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(FieldTask.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<FieldTaskResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(FieldTaskFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(FieldTaskFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(FieldTaskFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
