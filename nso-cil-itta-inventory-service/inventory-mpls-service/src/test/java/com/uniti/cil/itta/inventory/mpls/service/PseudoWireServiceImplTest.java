package com.uniti.cil.itta.inventory.mpls.service;

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
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.mapper.PseudoWireMapper;
import com.uniti.cil.itta.inventory.mpls.model.PseudoWire;
import com.uniti.cil.itta.inventory.mpls.repository.PseudoWireRepository;
import com.uniti.cil.itta.inventory.mpls.support.PseudoWireFixtures;
import com.uniti.cil.itta.inventory.mpls.validation.PseudoWireValidator;
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
class PseudoWireServiceImplTest {

    @Mock
    private PseudoWireRepository repository;
    @Mock
    private EventPublisher events;

    private PseudoWireServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PseudoWireFixtures.TENANT, "tester");
        service = new PseudoWireServiceImpl(repository, new PseudoWireMapper(), new PseudoWireValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PseudoWire entity = PseudoWireFixtures.entity();
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PseudoWireFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PseudoWireFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PseudoWireFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PseudoWireCreateRequest request = PseudoWireFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PseudoWireFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PseudoWire.class))).thenAnswer(inv -> inv.getArgument(0));
        PseudoWireResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PseudoWire> captor = ArgumentCaptor.forClass(PseudoWire.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PseudoWireFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PseudoWireCreateRequest request = PseudoWireFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PseudoWireFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PseudoWire entity = PseudoWireFixtures.entity();
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PseudoWire.class))).thenAnswer(inv -> inv.getArgument(0));
        PseudoWireUpdateRequest request = new PseudoWireUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PseudoWireResponse response = service.update(PseudoWireFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PseudoWire entity = PseudoWireFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.of(entity));
        PseudoWireUpdateRequest request = new PseudoWireUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PseudoWireFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PseudoWire entity = PseudoWireFixtures.entity();
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PseudoWireFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PseudoWire entity = PseudoWireFixtures.entity();
        when(repository.findByTenantIdAndId(PseudoWireFixtures.TENANT, PseudoWireFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PseudoWireFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PseudoWireFixtures.entity())));
        assertThat(service.search(PseudoWireFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PseudoWireCreateRequest ok = PseudoWireFixtures.createRequest();
        PseudoWireCreateRequest dup = PseudoWireFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PseudoWireFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PseudoWireFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PseudoWire.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PseudoWireResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PseudoWireFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PseudoWireFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PseudoWireFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
