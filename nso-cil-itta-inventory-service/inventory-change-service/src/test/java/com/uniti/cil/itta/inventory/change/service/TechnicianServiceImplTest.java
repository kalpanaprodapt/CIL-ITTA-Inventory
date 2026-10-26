package com.uniti.cil.itta.inventory.change.service;

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
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.mapper.TechnicianMapper;
import com.uniti.cil.itta.inventory.change.model.Technician;
import com.uniti.cil.itta.inventory.change.repository.TechnicianRepository;
import com.uniti.cil.itta.inventory.change.support.TechnicianFixtures;
import com.uniti.cil.itta.inventory.change.validation.TechnicianValidator;
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
class TechnicianServiceImplTest {

    @Mock
    private TechnicianRepository repository;
    @Mock
    private EventPublisher events;

    private TechnicianServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TechnicianFixtures.TENANT, "tester");
        service = new TechnicianServiceImpl(repository, new TechnicianMapper(), new TechnicianValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Technician entity = TechnicianFixtures.entity();
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TechnicianFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TechnicianFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TechnicianFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TechnicianCreateRequest request = TechnicianFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TechnicianFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Technician.class))).thenAnswer(inv -> inv.getArgument(0));
        TechnicianResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Technician> captor = ArgumentCaptor.forClass(Technician.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TechnicianFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TechnicianCreateRequest request = TechnicianFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TechnicianFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Technician entity = TechnicianFixtures.entity();
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Technician.class))).thenAnswer(inv -> inv.getArgument(0));
        TechnicianUpdateRequest request = new TechnicianUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TechnicianResponse response = service.update(TechnicianFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Technician entity = TechnicianFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.of(entity));
        TechnicianUpdateRequest request = new TechnicianUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TechnicianFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Technician entity = TechnicianFixtures.entity();
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TechnicianFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Technician entity = TechnicianFixtures.entity();
        when(repository.findByTenantIdAndId(TechnicianFixtures.TENANT, TechnicianFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TechnicianFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TechnicianFixtures.entity())));
        assertThat(service.search(TechnicianFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TechnicianCreateRequest ok = TechnicianFixtures.createRequest();
        TechnicianCreateRequest dup = TechnicianFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TechnicianFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TechnicianFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Technician.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TechnicianResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TechnicianFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TechnicianFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TechnicianFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
