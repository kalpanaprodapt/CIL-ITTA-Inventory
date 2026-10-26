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
import com.uniti.cil.itta.inventory.radio.mapper.SectorMapper;
import com.uniti.cil.itta.inventory.radio.model.Sector;
import com.uniti.cil.itta.inventory.radio.repository.SectorRepository;
import com.uniti.cil.itta.inventory.radio.support.SectorFixtures;
import com.uniti.cil.itta.inventory.radio.validation.SectorValidator;
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
class SectorServiceImplTest {

    @Mock
    private SectorRepository repository;
    @Mock
    private EventPublisher events;

    private SectorServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SectorFixtures.TENANT, "tester");
        service = new SectorServiceImpl(repository, new SectorMapper(), new SectorValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Sector entity = SectorFixtures.entity();
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SectorFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SectorFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SectorFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SectorCreateRequest request = SectorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SectorFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Sector.class))).thenAnswer(inv -> inv.getArgument(0));
        SectorResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Sector> captor = ArgumentCaptor.forClass(Sector.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SectorFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SectorCreateRequest request = SectorFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SectorFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Sector entity = SectorFixtures.entity();
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Sector.class))).thenAnswer(inv -> inv.getArgument(0));
        SectorUpdateRequest request = new SectorUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SectorResponse response = service.update(SectorFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Sector entity = SectorFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.of(entity));
        SectorUpdateRequest request = new SectorUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SectorFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Sector entity = SectorFixtures.entity();
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SectorFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Sector entity = SectorFixtures.entity();
        when(repository.findByTenantIdAndId(SectorFixtures.TENANT, SectorFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SectorFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SectorFixtures.entity())));
        assertThat(service.search(SectorFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SectorCreateRequest ok = SectorFixtures.createRequest();
        SectorCreateRequest dup = SectorFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SectorFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SectorFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Sector.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SectorResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SectorFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SectorFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SectorFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
