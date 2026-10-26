package com.uniti.cil.itta.inventory.software.service;

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
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.mapper.SoftwareVersionMapper;
import com.uniti.cil.itta.inventory.software.model.SoftwareVersion;
import com.uniti.cil.itta.inventory.software.repository.SoftwareVersionRepository;
import com.uniti.cil.itta.inventory.software.support.SoftwareVersionFixtures;
import com.uniti.cil.itta.inventory.software.validation.SoftwareVersionValidator;
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
class SoftwareVersionServiceImplTest {

    @Mock
    private SoftwareVersionRepository repository;
    @Mock
    private EventPublisher events;

    private SoftwareVersionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SoftwareVersionFixtures.TENANT, "tester");
        service = new SoftwareVersionServiceImpl(repository, new SoftwareVersionMapper(), new SoftwareVersionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SoftwareVersionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SoftwareVersionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SoftwareVersionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SoftwareVersionCreateRequest request = SoftwareVersionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SoftwareVersionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SoftwareVersion.class))).thenAnswer(inv -> inv.getArgument(0));
        SoftwareVersionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SoftwareVersion> captor = ArgumentCaptor.forClass(SoftwareVersion.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SoftwareVersionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SoftwareVersionCreateRequest request = SoftwareVersionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SoftwareVersionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SoftwareVersion.class))).thenAnswer(inv -> inv.getArgument(0));
        SoftwareVersionUpdateRequest request = new SoftwareVersionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SoftwareVersionResponse response = service.update(SoftwareVersionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.of(entity));
        SoftwareVersionUpdateRequest request = new SoftwareVersionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SoftwareVersionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SoftwareVersionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareVersionFixtures.TENANT, SoftwareVersionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SoftwareVersionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SoftwareVersionFixtures.entity())));
        assertThat(service.search(SoftwareVersionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SoftwareVersionCreateRequest ok = SoftwareVersionFixtures.createRequest();
        SoftwareVersionCreateRequest dup = SoftwareVersionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SoftwareVersionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SoftwareVersionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SoftwareVersion.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SoftwareVersionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SoftwareVersionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SoftwareVersionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SoftwareVersionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
