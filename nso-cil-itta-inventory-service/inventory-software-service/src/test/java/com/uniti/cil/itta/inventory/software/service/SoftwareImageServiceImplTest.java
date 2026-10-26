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
import com.uniti.cil.itta.inventory.software.mapper.SoftwareImageMapper;
import com.uniti.cil.itta.inventory.software.model.SoftwareImage;
import com.uniti.cil.itta.inventory.software.repository.SoftwareImageRepository;
import com.uniti.cil.itta.inventory.software.support.SoftwareImageFixtures;
import com.uniti.cil.itta.inventory.software.validation.SoftwareImageValidator;
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
class SoftwareImageServiceImplTest {

    @Mock
    private SoftwareImageRepository repository;
    @Mock
    private EventPublisher events;

    private SoftwareImageServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SoftwareImageFixtures.TENANT, "tester");
        service = new SoftwareImageServiceImpl(repository, new SoftwareImageMapper(), new SoftwareImageValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SoftwareImageFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SoftwareImageFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SoftwareImageFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SoftwareImageCreateRequest request = SoftwareImageFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SoftwareImageFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SoftwareImage.class))).thenAnswer(inv -> inv.getArgument(0));
        SoftwareImageResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SoftwareImage> captor = ArgumentCaptor.forClass(SoftwareImage.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SoftwareImageFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SoftwareImageCreateRequest request = SoftwareImageFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SoftwareImageFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SoftwareImage.class))).thenAnswer(inv -> inv.getArgument(0));
        SoftwareImageUpdateRequest request = new SoftwareImageUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SoftwareImageResponse response = service.update(SoftwareImageFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.of(entity));
        SoftwareImageUpdateRequest request = new SoftwareImageUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SoftwareImageFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SoftwareImageFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        when(repository.findByTenantIdAndId(SoftwareImageFixtures.TENANT, SoftwareImageFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SoftwareImageFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SoftwareImageFixtures.entity())));
        assertThat(service.search(SoftwareImageFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SoftwareImageCreateRequest ok = SoftwareImageFixtures.createRequest();
        SoftwareImageCreateRequest dup = SoftwareImageFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SoftwareImageFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SoftwareImageFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SoftwareImage.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SoftwareImageResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SoftwareImageFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SoftwareImageFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SoftwareImageFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
