package com.uniti.cil.itta.inventory.switching.service;

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
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.mapper.OspfAreaMapper;
import com.uniti.cil.itta.inventory.switching.model.OspfArea;
import com.uniti.cil.itta.inventory.switching.repository.OspfAreaRepository;
import com.uniti.cil.itta.inventory.switching.support.OspfAreaFixtures;
import com.uniti.cil.itta.inventory.switching.validation.OspfAreaValidator;
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
class OspfAreaServiceImplTest {

    @Mock
    private OspfAreaRepository repository;
    @Mock
    private EventPublisher events;

    private OspfAreaServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(OspfAreaFixtures.TENANT, "tester");
        service = new OspfAreaServiceImpl(repository, new OspfAreaMapper(), new OspfAreaValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        OspfArea entity = OspfAreaFixtures.entity();
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(OspfAreaFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(OspfAreaFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(OspfAreaFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        OspfAreaCreateRequest request = OspfAreaFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OspfAreaFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(OspfArea.class))).thenAnswer(inv -> inv.getArgument(0));
        OspfAreaResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<OspfArea> captor = ArgumentCaptor.forClass(OspfArea.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(OspfAreaFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        OspfAreaCreateRequest request = OspfAreaFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OspfAreaFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        OspfArea entity = OspfAreaFixtures.entity();
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(OspfArea.class))).thenAnswer(inv -> inv.getArgument(0));
        OspfAreaUpdateRequest request = new OspfAreaUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        OspfAreaResponse response = service.update(OspfAreaFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        OspfArea entity = OspfAreaFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.of(entity));
        OspfAreaUpdateRequest request = new OspfAreaUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(OspfAreaFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        OspfArea entity = OspfAreaFixtures.entity();
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(OspfAreaFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        OspfArea entity = OspfAreaFixtures.entity();
        when(repository.findByTenantIdAndId(OspfAreaFixtures.TENANT, OspfAreaFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(OspfAreaFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(OspfAreaFixtures.entity())));
        assertThat(service.search(OspfAreaFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        OspfAreaCreateRequest ok = OspfAreaFixtures.createRequest();
        OspfAreaCreateRequest dup = OspfAreaFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(OspfAreaFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(OspfAreaFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(OspfArea.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<OspfAreaResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(OspfAreaFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OspfAreaFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(OspfAreaFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
