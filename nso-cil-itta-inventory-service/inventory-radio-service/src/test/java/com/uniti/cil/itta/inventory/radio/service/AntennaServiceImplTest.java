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
import com.uniti.cil.itta.inventory.radio.mapper.AntennaMapper;
import com.uniti.cil.itta.inventory.radio.model.Antenna;
import com.uniti.cil.itta.inventory.radio.repository.AntennaRepository;
import com.uniti.cil.itta.inventory.radio.support.AntennaFixtures;
import com.uniti.cil.itta.inventory.radio.validation.AntennaValidator;
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
class AntennaServiceImplTest {

    @Mock
    private AntennaRepository repository;
    @Mock
    private EventPublisher events;

    private AntennaServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(AntennaFixtures.TENANT, "tester");
        service = new AntennaServiceImpl(repository, new AntennaMapper(), new AntennaValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Antenna entity = AntennaFixtures.entity();
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(AntennaFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(AntennaFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(AntennaFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        AntennaCreateRequest request = AntennaFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AntennaFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Antenna.class))).thenAnswer(inv -> inv.getArgument(0));
        AntennaResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Antenna> captor = ArgumentCaptor.forClass(Antenna.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(AntennaFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        AntennaCreateRequest request = AntennaFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AntennaFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Antenna entity = AntennaFixtures.entity();
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Antenna.class))).thenAnswer(inv -> inv.getArgument(0));
        AntennaUpdateRequest request = new AntennaUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        AntennaResponse response = service.update(AntennaFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Antenna entity = AntennaFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.of(entity));
        AntennaUpdateRequest request = new AntennaUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(AntennaFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Antenna entity = AntennaFixtures.entity();
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(AntennaFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Antenna entity = AntennaFixtures.entity();
        when(repository.findByTenantIdAndId(AntennaFixtures.TENANT, AntennaFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(AntennaFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(AntennaFixtures.entity())));
        assertThat(service.search(AntennaFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        AntennaCreateRequest ok = AntennaFixtures.createRequest();
        AntennaCreateRequest dup = AntennaFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(AntennaFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(AntennaFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Antenna.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<AntennaResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(AntennaFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AntennaFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(AntennaFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
