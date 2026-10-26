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
import com.uniti.cil.itta.inventory.alarm.mapper.AlarmDefinitionMapper;
import com.uniti.cil.itta.inventory.alarm.model.AlarmDefinition;
import com.uniti.cil.itta.inventory.alarm.repository.AlarmDefinitionRepository;
import com.uniti.cil.itta.inventory.alarm.support.AlarmDefinitionFixtures;
import com.uniti.cil.itta.inventory.alarm.validation.AlarmDefinitionValidator;
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
class AlarmDefinitionServiceImplTest {

    @Mock
    private AlarmDefinitionRepository repository;
    @Mock
    private EventPublisher events;

    private AlarmDefinitionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(AlarmDefinitionFixtures.TENANT, "tester");
        service = new AlarmDefinitionServiceImpl(repository, new AlarmDefinitionMapper(), new AlarmDefinitionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(AlarmDefinitionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(AlarmDefinitionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        AlarmDefinitionCreateRequest request = AlarmDefinitionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(AlarmDefinition.class))).thenAnswer(inv -> inv.getArgument(0));
        AlarmDefinitionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<AlarmDefinition> captor = ArgumentCaptor.forClass(AlarmDefinition.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(AlarmDefinitionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        AlarmDefinitionCreateRequest request = AlarmDefinitionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(AlarmDefinition.class))).thenAnswer(inv -> inv.getArgument(0));
        AlarmDefinitionUpdateRequest request = new AlarmDefinitionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        AlarmDefinitionResponse response = service.update(AlarmDefinitionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.of(entity));
        AlarmDefinitionUpdateRequest request = new AlarmDefinitionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(AlarmDefinitionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(AlarmDefinitionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        when(repository.findByTenantIdAndId(AlarmDefinitionFixtures.TENANT, AlarmDefinitionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(AlarmDefinitionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(AlarmDefinitionFixtures.entity())));
        assertThat(service.search(AlarmDefinitionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        AlarmDefinitionCreateRequest ok = AlarmDefinitionFixtures.createRequest();
        AlarmDefinitionCreateRequest dup = AlarmDefinitionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(AlarmDefinitionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(AlarmDefinition.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<AlarmDefinitionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(AlarmDefinitionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AlarmDefinitionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(AlarmDefinitionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
