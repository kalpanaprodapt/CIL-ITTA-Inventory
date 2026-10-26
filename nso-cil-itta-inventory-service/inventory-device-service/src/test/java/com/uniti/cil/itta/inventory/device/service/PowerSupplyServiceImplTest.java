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
import com.uniti.cil.itta.inventory.device.mapper.PowerSupplyMapper;
import com.uniti.cil.itta.inventory.device.model.PowerSupply;
import com.uniti.cil.itta.inventory.device.repository.PowerSupplyRepository;
import com.uniti.cil.itta.inventory.device.support.PowerSupplyFixtures;
import com.uniti.cil.itta.inventory.device.validation.PowerSupplyValidator;
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
class PowerSupplyServiceImplTest {

    @Mock
    private PowerSupplyRepository repository;
    @Mock
    private EventPublisher events;

    private PowerSupplyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PowerSupplyFixtures.TENANT, "tester");
        service = new PowerSupplyServiceImpl(repository, new PowerSupplyMapper(), new PowerSupplyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PowerSupplyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PowerSupplyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PowerSupplyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PowerSupplyCreateRequest request = PowerSupplyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PowerSupplyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PowerSupply.class))).thenAnswer(inv -> inv.getArgument(0));
        PowerSupplyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PowerSupply> captor = ArgumentCaptor.forClass(PowerSupply.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PowerSupplyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PowerSupplyCreateRequest request = PowerSupplyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PowerSupplyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PowerSupply.class))).thenAnswer(inv -> inv.getArgument(0));
        PowerSupplyUpdateRequest request = new PowerSupplyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PowerSupplyResponse response = service.update(PowerSupplyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.of(entity));
        PowerSupplyUpdateRequest request = new PowerSupplyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PowerSupplyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PowerSupplyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        when(repository.findByTenantIdAndId(PowerSupplyFixtures.TENANT, PowerSupplyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PowerSupplyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PowerSupplyFixtures.entity())));
        assertThat(service.search(PowerSupplyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PowerSupplyCreateRequest ok = PowerSupplyFixtures.createRequest();
        PowerSupplyCreateRequest dup = PowerSupplyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PowerSupplyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PowerSupplyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PowerSupply.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PowerSupplyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PowerSupplyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PowerSupplyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PowerSupplyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
