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
import com.uniti.cil.itta.inventory.change.mapper.ChangeRequestMapper;
import com.uniti.cil.itta.inventory.change.model.ChangeRequest;
import com.uniti.cil.itta.inventory.change.repository.ChangeRequestRepository;
import com.uniti.cil.itta.inventory.change.support.ChangeRequestFixtures;
import com.uniti.cil.itta.inventory.change.validation.ChangeRequestValidator;
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
class ChangeRequestServiceImplTest {

    @Mock
    private ChangeRequestRepository repository;
    @Mock
    private EventPublisher events;

    private ChangeRequestServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ChangeRequestFixtures.TENANT, "tester");
        service = new ChangeRequestServiceImpl(repository, new ChangeRequestMapper(), new ChangeRequestValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ChangeRequestFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ChangeRequestFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ChangeRequestFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ChangeRequestCreateRequest request = ChangeRequestFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ChangeRequestFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ChangeRequest.class))).thenAnswer(inv -> inv.getArgument(0));
        ChangeRequestResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ChangeRequest> captor = ArgumentCaptor.forClass(ChangeRequest.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ChangeRequestFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ChangeRequestCreateRequest request = ChangeRequestFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ChangeRequestFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ChangeRequest.class))).thenAnswer(inv -> inv.getArgument(0));
        ChangeRequestUpdateRequest request = new ChangeRequestUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ChangeRequestResponse response = service.update(ChangeRequestFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.of(entity));
        ChangeRequestUpdateRequest request = new ChangeRequestUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ChangeRequestFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ChangeRequestFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        when(repository.findByTenantIdAndId(ChangeRequestFixtures.TENANT, ChangeRequestFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ChangeRequestFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ChangeRequestFixtures.entity())));
        assertThat(service.search(ChangeRequestFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ChangeRequestCreateRequest ok = ChangeRequestFixtures.createRequest();
        ChangeRequestCreateRequest dup = ChangeRequestFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ChangeRequestFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ChangeRequestFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ChangeRequest.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ChangeRequestResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ChangeRequestFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ChangeRequestFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ChangeRequestFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
