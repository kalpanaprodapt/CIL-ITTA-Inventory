package com.uniti.cil.itta.inventory.port.service;

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
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.mapper.TransceiverMapper;
import com.uniti.cil.itta.inventory.port.model.Transceiver;
import com.uniti.cil.itta.inventory.port.repository.TransceiverRepository;
import com.uniti.cil.itta.inventory.port.support.TransceiverFixtures;
import com.uniti.cil.itta.inventory.port.validation.TransceiverValidator;
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
class TransceiverServiceImplTest {

    @Mock
    private TransceiverRepository repository;
    @Mock
    private EventPublisher events;

    private TransceiverServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TransceiverFixtures.TENANT, "tester");
        service = new TransceiverServiceImpl(repository, new TransceiverMapper(), new TransceiverValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Transceiver entity = TransceiverFixtures.entity();
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TransceiverFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TransceiverFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TransceiverFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TransceiverCreateRequest request = TransceiverFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TransceiverFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Transceiver.class))).thenAnswer(inv -> inv.getArgument(0));
        TransceiverResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Transceiver> captor = ArgumentCaptor.forClass(Transceiver.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TransceiverFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TransceiverCreateRequest request = TransceiverFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TransceiverFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Transceiver entity = TransceiverFixtures.entity();
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Transceiver.class))).thenAnswer(inv -> inv.getArgument(0));
        TransceiverUpdateRequest request = new TransceiverUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TransceiverResponse response = service.update(TransceiverFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Transceiver entity = TransceiverFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.of(entity));
        TransceiverUpdateRequest request = new TransceiverUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TransceiverFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Transceiver entity = TransceiverFixtures.entity();
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TransceiverFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Transceiver entity = TransceiverFixtures.entity();
        when(repository.findByTenantIdAndId(TransceiverFixtures.TENANT, TransceiverFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TransceiverFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TransceiverFixtures.entity())));
        assertThat(service.search(TransceiverFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TransceiverCreateRequest ok = TransceiverFixtures.createRequest();
        TransceiverCreateRequest dup = TransceiverFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TransceiverFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TransceiverFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Transceiver.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TransceiverResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TransceiverFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TransceiverFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TransceiverFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
