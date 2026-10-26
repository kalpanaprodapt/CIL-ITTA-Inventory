package com.uniti.cil.itta.inventory.alarm.service;

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
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.mapper.AlarmInstanceMapper;
import com.uniti.cil.itta.inventory.alarm.model.AlarmInstance;
import com.uniti.cil.itta.inventory.alarm.repository.AlarmInstanceRepository;
import com.uniti.cil.itta.inventory.alarm.support.AlarmInstanceFixtures;
import com.uniti.cil.itta.inventory.alarm.validation.AlarmInstanceValidator;
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
class AlarmInstanceServiceImplTest {

    @Mock
    private AlarmInstanceRepository repository;
    @Mock
    private EventPublisher events;

    private AlarmInstanceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(AlarmInstanceFixtures.TENANT, "tester");
        service = new AlarmInstanceServiceImpl(repository, new AlarmInstanceMapper(), new AlarmInstanceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(AlarmInstanceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(AlarmInstanceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(AlarmInstanceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        AlarmInstanceCreateRequest request = AlarmInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AlarmInstanceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(AlarmInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        AlarmInstanceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<AlarmInstance> captor = ArgumentCaptor.forClass(AlarmInstance.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(AlarmInstanceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        AlarmInstanceCreateRequest request = AlarmInstanceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AlarmInstanceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(AlarmInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        AlarmInstanceUpdateRequest request = new AlarmInstanceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        AlarmInstanceResponse response = service.update(AlarmInstanceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        AlarmInstanceUpdateRequest request = new AlarmInstanceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(AlarmInstanceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(AlarmInstanceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmInstanceFixtures.TENANT, AlarmInstanceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(AlarmInstanceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(AlarmInstanceFixtures.entity())));
        assertThat(service.search(AlarmInstanceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        AlarmInstanceCreateRequest ok = AlarmInstanceFixtures.createRequest();
        AlarmInstanceCreateRequest dup = AlarmInstanceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(AlarmInstanceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(AlarmInstanceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(AlarmInstance.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<AlarmInstanceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(AlarmInstanceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AlarmInstanceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(AlarmInstanceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
