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
import com.uniti.cil.itta.inventory.port.mapper.LagGroupMapper;
import com.uniti.cil.itta.inventory.port.model.LagGroup;
import com.uniti.cil.itta.inventory.port.repository.LagGroupRepository;
import com.uniti.cil.itta.inventory.port.support.LagGroupFixtures;
import com.uniti.cil.itta.inventory.port.validation.LagGroupValidator;
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
class LagGroupServiceImplTest {

    @Mock
    private LagGroupRepository repository;
    @Mock
    private EventPublisher events;

    private LagGroupServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(LagGroupFixtures.TENANT, "tester");
        service = new LagGroupServiceImpl(repository, new LagGroupMapper(), new LagGroupValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        LagGroup entity = LagGroupFixtures.entity();
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(LagGroupFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(LagGroupFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(LagGroupFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        LagGroupCreateRequest request = LagGroupFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LagGroupFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(LagGroup.class))).thenAnswer(inv -> inv.getArgument(0));
        LagGroupResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<LagGroup> captor = ArgumentCaptor.forClass(LagGroup.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(LagGroupFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        LagGroupCreateRequest request = LagGroupFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LagGroupFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        LagGroup entity = LagGroupFixtures.entity();
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(LagGroup.class))).thenAnswer(inv -> inv.getArgument(0));
        LagGroupUpdateRequest request = new LagGroupUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        LagGroupResponse response = service.update(LagGroupFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        LagGroup entity = LagGroupFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.of(entity));
        LagGroupUpdateRequest request = new LagGroupUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(LagGroupFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        LagGroup entity = LagGroupFixtures.entity();
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(LagGroupFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        LagGroup entity = LagGroupFixtures.entity();
        when(repository.findByTenantIdAndId(LagGroupFixtures.TENANT, LagGroupFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(LagGroupFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(LagGroupFixtures.entity())));
        assertThat(service.search(LagGroupFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        LagGroupCreateRequest ok = LagGroupFixtures.createRequest();
        LagGroupCreateRequest dup = LagGroupFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(LagGroupFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(LagGroupFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(LagGroup.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<LagGroupResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(LagGroupFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(LagGroupFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(LagGroupFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
