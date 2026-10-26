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
import com.uniti.cil.itta.inventory.change.mapper.MaintenanceWindowMapper;
import com.uniti.cil.itta.inventory.change.model.MaintenanceWindow;
import com.uniti.cil.itta.inventory.change.repository.MaintenanceWindowRepository;
import com.uniti.cil.itta.inventory.change.support.MaintenanceWindowFixtures;
import com.uniti.cil.itta.inventory.change.validation.MaintenanceWindowValidator;
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
class MaintenanceWindowServiceImplTest {

    @Mock
    private MaintenanceWindowRepository repository;
    @Mock
    private EventPublisher events;

    private MaintenanceWindowServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(MaintenanceWindowFixtures.TENANT, "tester");
        service = new MaintenanceWindowServiceImpl(repository, new MaintenanceWindowMapper(), new MaintenanceWindowValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(MaintenanceWindowFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(MaintenanceWindowFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(MaintenanceWindowFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        MaintenanceWindowCreateRequest request = MaintenanceWindowFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(MaintenanceWindowFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(MaintenanceWindow.class))).thenAnswer(inv -> inv.getArgument(0));
        MaintenanceWindowResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<MaintenanceWindow> captor = ArgumentCaptor.forClass(MaintenanceWindow.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(MaintenanceWindowFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        MaintenanceWindowCreateRequest request = MaintenanceWindowFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(MaintenanceWindowFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(MaintenanceWindow.class))).thenAnswer(inv -> inv.getArgument(0));
        MaintenanceWindowUpdateRequest request = new MaintenanceWindowUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        MaintenanceWindowResponse response = service.update(MaintenanceWindowFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.of(entity));
        MaintenanceWindowUpdateRequest request = new MaintenanceWindowUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(MaintenanceWindowFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(MaintenanceWindowFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        when(repository.findByTenantIdAndId(MaintenanceWindowFixtures.TENANT, MaintenanceWindowFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(MaintenanceWindowFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(MaintenanceWindowFixtures.entity())));
        assertThat(service.search(MaintenanceWindowFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        MaintenanceWindowCreateRequest ok = MaintenanceWindowFixtures.createRequest();
        MaintenanceWindowCreateRequest dup = MaintenanceWindowFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(MaintenanceWindowFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(MaintenanceWindowFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(MaintenanceWindow.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<MaintenanceWindowResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(MaintenanceWindowFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(MaintenanceWindowFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(MaintenanceWindowFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
