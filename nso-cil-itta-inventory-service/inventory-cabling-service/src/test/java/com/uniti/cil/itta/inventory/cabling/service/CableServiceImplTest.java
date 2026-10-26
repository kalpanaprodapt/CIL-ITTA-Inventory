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
import com.uniti.cil.itta.inventory.cabling.mapper.CableMapper;
import com.uniti.cil.itta.inventory.cabling.model.Cable;
import com.uniti.cil.itta.inventory.cabling.repository.CableRepository;
import com.uniti.cil.itta.inventory.cabling.support.CableFixtures;
import com.uniti.cil.itta.inventory.cabling.validation.CableValidator;
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
class CableServiceImplTest {

    @Mock
    private CableRepository repository;
    @Mock
    private EventPublisher events;

    private CableServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CableFixtures.TENANT, "tester");
        service = new CableServiceImpl(repository, new CableMapper(), new CableValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Cable entity = CableFixtures.entity();
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CableFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CableFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CableFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CableCreateRequest request = CableFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CableFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Cable.class))).thenAnswer(inv -> inv.getArgument(0));
        CableResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Cable> captor = ArgumentCaptor.forClass(Cable.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CableFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CableCreateRequest request = CableFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CableFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Cable entity = CableFixtures.entity();
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Cable.class))).thenAnswer(inv -> inv.getArgument(0));
        CableUpdateRequest request = new CableUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CableResponse response = service.update(CableFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Cable entity = CableFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.of(entity));
        CableUpdateRequest request = new CableUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CableFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Cable entity = CableFixtures.entity();
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CableFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Cable entity = CableFixtures.entity();
        when(repository.findByTenantIdAndId(CableFixtures.TENANT, CableFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CableFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CableFixtures.entity())));
        assertThat(service.search(CableFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CableCreateRequest ok = CableFixtures.createRequest();
        CableCreateRequest dup = CableFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CableFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CableFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Cable.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CableResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CableFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CableFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CableFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
