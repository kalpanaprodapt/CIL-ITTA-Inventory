package com.uniti.cil.itta.inventory.radio.service;

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
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.mapper.RadioUnitMapper;
import com.uniti.cil.itta.inventory.radio.model.RadioUnit;
import com.uniti.cil.itta.inventory.radio.repository.RadioUnitRepository;
import com.uniti.cil.itta.inventory.radio.support.RadioUnitFixtures;
import com.uniti.cil.itta.inventory.radio.validation.RadioUnitValidator;
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
class RadioUnitServiceImplTest {

    @Mock
    private RadioUnitRepository repository;
    @Mock
    private EventPublisher events;

    private RadioUnitServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RadioUnitFixtures.TENANT, "tester");
        service = new RadioUnitServiceImpl(repository, new RadioUnitMapper(), new RadioUnitValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        RadioUnit entity = RadioUnitFixtures.entity();
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RadioUnitFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RadioUnitFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RadioUnitFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RadioUnitCreateRequest request = RadioUnitFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RadioUnitFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(RadioUnit.class))).thenAnswer(inv -> inv.getArgument(0));
        RadioUnitResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<RadioUnit> captor = ArgumentCaptor.forClass(RadioUnit.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RadioUnitFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RadioUnitCreateRequest request = RadioUnitFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RadioUnitFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        RadioUnit entity = RadioUnitFixtures.entity();
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(RadioUnit.class))).thenAnswer(inv -> inv.getArgument(0));
        RadioUnitUpdateRequest request = new RadioUnitUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RadioUnitResponse response = service.update(RadioUnitFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        RadioUnit entity = RadioUnitFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.of(entity));
        RadioUnitUpdateRequest request = new RadioUnitUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RadioUnitFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        RadioUnit entity = RadioUnitFixtures.entity();
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RadioUnitFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        RadioUnit entity = RadioUnitFixtures.entity();
        when(repository.findByTenantIdAndId(RadioUnitFixtures.TENANT, RadioUnitFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RadioUnitFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RadioUnitFixtures.entity())));
        assertThat(service.search(RadioUnitFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RadioUnitCreateRequest ok = RadioUnitFixtures.createRequest();
        RadioUnitCreateRequest dup = RadioUnitFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RadioUnitFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RadioUnitFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(RadioUnit.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RadioUnitResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RadioUnitFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RadioUnitFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RadioUnitFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
