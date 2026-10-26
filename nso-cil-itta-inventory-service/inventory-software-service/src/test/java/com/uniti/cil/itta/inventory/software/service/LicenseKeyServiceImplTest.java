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
import com.uniti.cil.itta.inventory.software.mapper.LicenseKeyMapper;
import com.uniti.cil.itta.inventory.software.model.LicenseKey;
import com.uniti.cil.itta.inventory.software.repository.LicenseKeyRepository;
import com.uniti.cil.itta.inventory.software.support.LicenseKeyFixtures;
import com.uniti.cil.itta.inventory.software.validation.LicenseKeyValidator;
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
class LicenseKeyServiceImplTest {

    @Mock
    private LicenseKeyRepository repository;
    @Mock
    private EventPublisher events;

    private LicenseKeyServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(LicenseKeyFixtures.TENANT, "tester");
        service = new LicenseKeyServiceImpl(repository, new LicenseKeyMapper(), new LicenseKeyValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(LicenseKeyFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(LicenseKeyFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(LicenseKeyFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        LicenseKeyCreateRequest request = LicenseKeyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LicenseKeyFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(LicenseKey.class))).thenAnswer(inv -> inv.getArgument(0));
        LicenseKeyResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<LicenseKey> captor = ArgumentCaptor.forClass(LicenseKey.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(LicenseKeyFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        LicenseKeyCreateRequest request = LicenseKeyFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LicenseKeyFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(LicenseKey.class))).thenAnswer(inv -> inv.getArgument(0));
        LicenseKeyUpdateRequest request = new LicenseKeyUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        LicenseKeyResponse response = service.update(LicenseKeyFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.of(entity));
        LicenseKeyUpdateRequest request = new LicenseKeyUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(LicenseKeyFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(LicenseKeyFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        when(repository.findByTenantIdAndId(LicenseKeyFixtures.TENANT, LicenseKeyFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(LicenseKeyFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(LicenseKeyFixtures.entity())));
        assertThat(service.search(LicenseKeyFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        LicenseKeyCreateRequest ok = LicenseKeyFixtures.createRequest();
        LicenseKeyCreateRequest dup = LicenseKeyFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(LicenseKeyFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(LicenseKeyFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(LicenseKey.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<LicenseKeyResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(LicenseKeyFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(LicenseKeyFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(LicenseKeyFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
