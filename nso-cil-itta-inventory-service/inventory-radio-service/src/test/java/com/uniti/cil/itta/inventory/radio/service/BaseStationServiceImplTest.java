package com.uniti.cil.itta.inventory.radio.service;

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
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.mapper.BaseStationMapper;
import com.uniti.cil.itta.inventory.radio.model.BaseStation;
import com.uniti.cil.itta.inventory.radio.repository.BaseStationRepository;
import com.uniti.cil.itta.inventory.radio.support.BaseStationFixtures;
import com.uniti.cil.itta.inventory.radio.validation.BaseStationValidator;
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
class BaseStationServiceImplTest {

    @Mock
    private BaseStationRepository repository;
    @Mock
    private EventPublisher events;

    private BaseStationServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(BaseStationFixtures.TENANT, "tester");
        service = new BaseStationServiceImpl(repository, new BaseStationMapper(), new BaseStationValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        BaseStation entity = BaseStationFixtures.entity();
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(BaseStationFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(BaseStationFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(BaseStationFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        BaseStationCreateRequest request = BaseStationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BaseStationFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(BaseStation.class))).thenAnswer(inv -> inv.getArgument(0));
        BaseStationResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<BaseStation> captor = ArgumentCaptor.forClass(BaseStation.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(BaseStationFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        BaseStationCreateRequest request = BaseStationFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BaseStationFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        BaseStation entity = BaseStationFixtures.entity();
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(BaseStation.class))).thenAnswer(inv -> inv.getArgument(0));
        BaseStationUpdateRequest request = new BaseStationUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        BaseStationResponse response = service.update(BaseStationFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        BaseStation entity = BaseStationFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.of(entity));
        BaseStationUpdateRequest request = new BaseStationUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(BaseStationFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        BaseStation entity = BaseStationFixtures.entity();
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(BaseStationFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        BaseStation entity = BaseStationFixtures.entity();
        when(repository.findByTenantIdAndId(BaseStationFixtures.TENANT, BaseStationFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(BaseStationFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(BaseStationFixtures.entity())));
        assertThat(service.search(BaseStationFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        BaseStationCreateRequest ok = BaseStationFixtures.createRequest();
        BaseStationCreateRequest dup = BaseStationFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(BaseStationFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(BaseStationFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(BaseStation.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<BaseStationResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(BaseStationFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(BaseStationFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(BaseStationFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
