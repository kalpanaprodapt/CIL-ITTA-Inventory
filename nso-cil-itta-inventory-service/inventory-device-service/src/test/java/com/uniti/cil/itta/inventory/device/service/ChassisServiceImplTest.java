package com.uniti.cil.itta.inventory.device.service;

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
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.mapper.ChassisMapper;
import com.uniti.cil.itta.inventory.device.model.Chassis;
import com.uniti.cil.itta.inventory.device.repository.ChassisRepository;
import com.uniti.cil.itta.inventory.device.support.ChassisFixtures;
import com.uniti.cil.itta.inventory.device.validation.ChassisValidator;
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
class ChassisServiceImplTest {

    @Mock
    private ChassisRepository repository;
    @Mock
    private EventPublisher events;

    private ChassisServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ChassisFixtures.TENANT, "tester");
        service = new ChassisServiceImpl(repository, new ChassisMapper(), new ChassisValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Chassis entity = ChassisFixtures.entity();
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ChassisFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ChassisFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ChassisFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ChassisCreateRequest request = ChassisFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ChassisFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Chassis.class))).thenAnswer(inv -> inv.getArgument(0));
        ChassisResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Chassis> captor = ArgumentCaptor.forClass(Chassis.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ChassisFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ChassisCreateRequest request = ChassisFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ChassisFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Chassis entity = ChassisFixtures.entity();
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Chassis.class))).thenAnswer(inv -> inv.getArgument(0));
        ChassisUpdateRequest request = new ChassisUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ChassisResponse response = service.update(ChassisFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Chassis entity = ChassisFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.of(entity));
        ChassisUpdateRequest request = new ChassisUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ChassisFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Chassis entity = ChassisFixtures.entity();
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ChassisFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Chassis entity = ChassisFixtures.entity();
        when(repository.findByTenantIdAndId(ChassisFixtures.TENANT, ChassisFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ChassisFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ChassisFixtures.entity())));
        assertThat(service.search(ChassisFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ChassisCreateRequest ok = ChassisFixtures.createRequest();
        ChassisCreateRequest dup = ChassisFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ChassisFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ChassisFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Chassis.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ChassisResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ChassisFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ChassisFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ChassisFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
