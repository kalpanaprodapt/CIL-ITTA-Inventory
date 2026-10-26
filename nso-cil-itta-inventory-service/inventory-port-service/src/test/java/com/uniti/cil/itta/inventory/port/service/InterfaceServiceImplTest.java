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
import com.uniti.cil.itta.inventory.port.mapper.InterfaceMapper;
import com.uniti.cil.itta.inventory.port.model.Interface;
import com.uniti.cil.itta.inventory.port.repository.InterfaceRepository;
import com.uniti.cil.itta.inventory.port.support.InterfaceFixtures;
import com.uniti.cil.itta.inventory.port.validation.InterfaceValidator;
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
class InterfaceServiceImplTest {

    @Mock
    private InterfaceRepository repository;
    @Mock
    private EventPublisher events;

    private InterfaceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(InterfaceFixtures.TENANT, "tester");
        service = new InterfaceServiceImpl(repository, new InterfaceMapper(), new InterfaceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Interface entity = InterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(InterfaceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(InterfaceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(InterfaceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        InterfaceCreateRequest request = InterfaceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(InterfaceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Interface.class))).thenAnswer(inv -> inv.getArgument(0));
        InterfaceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Interface> captor = ArgumentCaptor.forClass(Interface.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(InterfaceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        InterfaceCreateRequest request = InterfaceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(InterfaceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Interface entity = InterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Interface.class))).thenAnswer(inv -> inv.getArgument(0));
        InterfaceUpdateRequest request = new InterfaceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        InterfaceResponse response = service.update(InterfaceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Interface entity = InterfaceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        InterfaceUpdateRequest request = new InterfaceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(InterfaceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Interface entity = InterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(InterfaceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Interface entity = InterfaceFixtures.entity();
        when(repository.findByTenantIdAndId(InterfaceFixtures.TENANT, InterfaceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(InterfaceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(InterfaceFixtures.entity())));
        assertThat(service.search(InterfaceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        InterfaceCreateRequest ok = InterfaceFixtures.createRequest();
        InterfaceCreateRequest dup = InterfaceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(InterfaceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(InterfaceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Interface.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<InterfaceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(InterfaceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(InterfaceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(InterfaceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
