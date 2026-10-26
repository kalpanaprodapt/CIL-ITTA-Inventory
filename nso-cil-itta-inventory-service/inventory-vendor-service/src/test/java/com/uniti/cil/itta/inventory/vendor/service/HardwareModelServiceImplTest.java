package com.uniti.cil.itta.inventory.vendor.service;

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
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.mapper.HardwareModelMapper;
import com.uniti.cil.itta.inventory.vendor.model.HardwareModel;
import com.uniti.cil.itta.inventory.vendor.repository.HardwareModelRepository;
import com.uniti.cil.itta.inventory.vendor.support.HardwareModelFixtures;
import com.uniti.cil.itta.inventory.vendor.validation.HardwareModelValidator;
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
class HardwareModelServiceImplTest {

    @Mock
    private HardwareModelRepository repository;
    @Mock
    private EventPublisher events;

    private HardwareModelServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(HardwareModelFixtures.TENANT, "tester");
        service = new HardwareModelServiceImpl(repository, new HardwareModelMapper(), new HardwareModelValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        HardwareModel entity = HardwareModelFixtures.entity();
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(HardwareModelFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(HardwareModelFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(HardwareModelFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        HardwareModelCreateRequest request = HardwareModelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(HardwareModelFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(HardwareModel.class))).thenAnswer(inv -> inv.getArgument(0));
        HardwareModelResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<HardwareModel> captor = ArgumentCaptor.forClass(HardwareModel.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(HardwareModelFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        HardwareModelCreateRequest request = HardwareModelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(HardwareModelFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        HardwareModel entity = HardwareModelFixtures.entity();
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(HardwareModel.class))).thenAnswer(inv -> inv.getArgument(0));
        HardwareModelUpdateRequest request = new HardwareModelUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        HardwareModelResponse response = service.update(HardwareModelFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        HardwareModel entity = HardwareModelFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.of(entity));
        HardwareModelUpdateRequest request = new HardwareModelUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(HardwareModelFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        HardwareModel entity = HardwareModelFixtures.entity();
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(HardwareModelFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        HardwareModel entity = HardwareModelFixtures.entity();
        when(repository.findByTenantIdAndId(HardwareModelFixtures.TENANT, HardwareModelFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(HardwareModelFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(HardwareModelFixtures.entity())));
        assertThat(service.search(HardwareModelFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        HardwareModelCreateRequest ok = HardwareModelFixtures.createRequest();
        HardwareModelCreateRequest dup = HardwareModelFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(HardwareModelFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(HardwareModelFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(HardwareModel.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<HardwareModelResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(HardwareModelFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(HardwareModelFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(HardwareModelFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
