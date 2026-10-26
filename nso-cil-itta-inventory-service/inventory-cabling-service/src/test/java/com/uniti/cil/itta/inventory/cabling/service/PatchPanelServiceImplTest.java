package com.uniti.cil.itta.inventory.cabling.service;

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
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.mapper.PatchPanelMapper;
import com.uniti.cil.itta.inventory.cabling.model.PatchPanel;
import com.uniti.cil.itta.inventory.cabling.repository.PatchPanelRepository;
import com.uniti.cil.itta.inventory.cabling.support.PatchPanelFixtures;
import com.uniti.cil.itta.inventory.cabling.validation.PatchPanelValidator;
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
class PatchPanelServiceImplTest {

    @Mock
    private PatchPanelRepository repository;
    @Mock
    private EventPublisher events;

    private PatchPanelServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PatchPanelFixtures.TENANT, "tester");
        service = new PatchPanelServiceImpl(repository, new PatchPanelMapper(), new PatchPanelValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PatchPanel entity = PatchPanelFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PatchPanelFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PatchPanelFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PatchPanelFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PatchPanelCreateRequest request = PatchPanelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchPanelFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PatchPanel.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchPanelResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PatchPanel> captor = ArgumentCaptor.forClass(PatchPanel.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PatchPanelFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PatchPanelCreateRequest request = PatchPanelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PatchPanelFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PatchPanel entity = PatchPanelFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PatchPanel.class))).thenAnswer(inv -> inv.getArgument(0));
        PatchPanelUpdateRequest request = new PatchPanelUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PatchPanelResponse response = service.update(PatchPanelFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PatchPanel entity = PatchPanelFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.of(entity));
        PatchPanelUpdateRequest request = new PatchPanelUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PatchPanelFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PatchPanel entity = PatchPanelFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PatchPanelFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PatchPanel entity = PatchPanelFixtures.entity();
        when(repository.findByTenantIdAndId(PatchPanelFixtures.TENANT, PatchPanelFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PatchPanelFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PatchPanelFixtures.entity())));
        assertThat(service.search(PatchPanelFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PatchPanelCreateRequest ok = PatchPanelFixtures.createRequest();
        PatchPanelCreateRequest dup = PatchPanelFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PatchPanelFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PatchPanelFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PatchPanel.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PatchPanelResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PatchPanelFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PatchPanelFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PatchPanelFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
