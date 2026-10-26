package com.uniti.cil.itta.inventory.capacity.service;

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
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.mapper.ForecastModelMapper;
import com.uniti.cil.itta.inventory.capacity.model.ForecastModel;
import com.uniti.cil.itta.inventory.capacity.repository.ForecastModelRepository;
import com.uniti.cil.itta.inventory.capacity.support.ForecastModelFixtures;
import com.uniti.cil.itta.inventory.capacity.validation.ForecastModelValidator;
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
class ForecastModelServiceImplTest {

    @Mock
    private ForecastModelRepository repository;
    @Mock
    private EventPublisher events;

    private ForecastModelServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(ForecastModelFixtures.TENANT, "tester");
        service = new ForecastModelServiceImpl(repository, new ForecastModelMapper(), new ForecastModelValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        ForecastModel entity = ForecastModelFixtures.entity();
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(ForecastModelFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(ForecastModelFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(ForecastModelFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        ForecastModelCreateRequest request = ForecastModelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ForecastModelFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(ForecastModel.class))).thenAnswer(inv -> inv.getArgument(0));
        ForecastModelResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<ForecastModel> captor = ArgumentCaptor.forClass(ForecastModel.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(ForecastModelFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        ForecastModelCreateRequest request = ForecastModelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(ForecastModelFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        ForecastModel entity = ForecastModelFixtures.entity();
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(ForecastModel.class))).thenAnswer(inv -> inv.getArgument(0));
        ForecastModelUpdateRequest request = new ForecastModelUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        ForecastModelResponse response = service.update(ForecastModelFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        ForecastModel entity = ForecastModelFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.of(entity));
        ForecastModelUpdateRequest request = new ForecastModelUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(ForecastModelFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        ForecastModel entity = ForecastModelFixtures.entity();
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(ForecastModelFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        ForecastModel entity = ForecastModelFixtures.entity();
        when(repository.findByTenantIdAndId(ForecastModelFixtures.TENANT, ForecastModelFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(ForecastModelFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(ForecastModelFixtures.entity())));
        assertThat(service.search(ForecastModelFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        ForecastModelCreateRequest ok = ForecastModelFixtures.createRequest();
        ForecastModelCreateRequest dup = ForecastModelFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(ForecastModelFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(ForecastModelFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(ForecastModel.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<ForecastModelResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(ForecastModelFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(ForecastModelFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(ForecastModelFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
