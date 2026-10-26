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
import com.uniti.cil.itta.inventory.capacity.mapper.UtilizationSampleMapper;
import com.uniti.cil.itta.inventory.capacity.model.UtilizationSample;
import com.uniti.cil.itta.inventory.capacity.repository.UtilizationSampleRepository;
import com.uniti.cil.itta.inventory.capacity.support.UtilizationSampleFixtures;
import com.uniti.cil.itta.inventory.capacity.validation.UtilizationSampleValidator;
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
class UtilizationSampleServiceImplTest {

    @Mock
    private UtilizationSampleRepository repository;
    @Mock
    private EventPublisher events;

    private UtilizationSampleServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(UtilizationSampleFixtures.TENANT, "tester");
        service = new UtilizationSampleServiceImpl(repository, new UtilizationSampleMapper(), new UtilizationSampleValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(UtilizationSampleFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(UtilizationSampleFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(UtilizationSampleFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        UtilizationSampleCreateRequest request = UtilizationSampleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(UtilizationSampleFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(UtilizationSample.class))).thenAnswer(inv -> inv.getArgument(0));
        UtilizationSampleResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<UtilizationSample> captor = ArgumentCaptor.forClass(UtilizationSample.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(UtilizationSampleFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        UtilizationSampleCreateRequest request = UtilizationSampleFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(UtilizationSampleFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(UtilizationSample.class))).thenAnswer(inv -> inv.getArgument(0));
        UtilizationSampleUpdateRequest request = new UtilizationSampleUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        UtilizationSampleResponse response = service.update(UtilizationSampleFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.of(entity));
        UtilizationSampleUpdateRequest request = new UtilizationSampleUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(UtilizationSampleFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(UtilizationSampleFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        when(repository.findByTenantIdAndId(UtilizationSampleFixtures.TENANT, UtilizationSampleFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(UtilizationSampleFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(UtilizationSampleFixtures.entity())));
        assertThat(service.search(UtilizationSampleFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        UtilizationSampleCreateRequest ok = UtilizationSampleFixtures.createRequest();
        UtilizationSampleCreateRequest dup = UtilizationSampleFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(UtilizationSampleFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(UtilizationSampleFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(UtilizationSample.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<UtilizationSampleResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(UtilizationSampleFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(UtilizationSampleFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(UtilizationSampleFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
