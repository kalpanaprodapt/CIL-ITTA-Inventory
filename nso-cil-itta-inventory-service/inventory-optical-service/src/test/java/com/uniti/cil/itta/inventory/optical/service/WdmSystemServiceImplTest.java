package com.uniti.cil.itta.inventory.optical.service;

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
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.mapper.WdmSystemMapper;
import com.uniti.cil.itta.inventory.optical.model.WdmSystem;
import com.uniti.cil.itta.inventory.optical.repository.WdmSystemRepository;
import com.uniti.cil.itta.inventory.optical.support.WdmSystemFixtures;
import com.uniti.cil.itta.inventory.optical.validation.WdmSystemValidator;
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
class WdmSystemServiceImplTest {

    @Mock
    private WdmSystemRepository repository;
    @Mock
    private EventPublisher events;

    private WdmSystemServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(WdmSystemFixtures.TENANT, "tester");
        service = new WdmSystemServiceImpl(repository, new WdmSystemMapper(), new WdmSystemValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        WdmSystem entity = WdmSystemFixtures.entity();
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(WdmSystemFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(WdmSystemFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(WdmSystemFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        WdmSystemCreateRequest request = WdmSystemFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WdmSystemFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(WdmSystem.class))).thenAnswer(inv -> inv.getArgument(0));
        WdmSystemResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<WdmSystem> captor = ArgumentCaptor.forClass(WdmSystem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(WdmSystemFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        WdmSystemCreateRequest request = WdmSystemFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WdmSystemFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        WdmSystem entity = WdmSystemFixtures.entity();
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(WdmSystem.class))).thenAnswer(inv -> inv.getArgument(0));
        WdmSystemUpdateRequest request = new WdmSystemUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        WdmSystemResponse response = service.update(WdmSystemFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        WdmSystem entity = WdmSystemFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.of(entity));
        WdmSystemUpdateRequest request = new WdmSystemUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(WdmSystemFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        WdmSystem entity = WdmSystemFixtures.entity();
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(WdmSystemFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        WdmSystem entity = WdmSystemFixtures.entity();
        when(repository.findByTenantIdAndId(WdmSystemFixtures.TENANT, WdmSystemFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(WdmSystemFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(WdmSystemFixtures.entity())));
        assertThat(service.search(WdmSystemFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        WdmSystemCreateRequest ok = WdmSystemFixtures.createRequest();
        WdmSystemCreateRequest dup = WdmSystemFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(WdmSystemFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(WdmSystemFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(WdmSystem.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<WdmSystemResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(WdmSystemFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(WdmSystemFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(WdmSystemFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
