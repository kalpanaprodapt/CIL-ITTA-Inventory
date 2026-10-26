package com.uniti.cil.itta.inventory.device.service;

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
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.mapper.FanTrayMapper;
import com.uniti.cil.itta.inventory.device.model.FanTray;
import com.uniti.cil.itta.inventory.device.repository.FanTrayRepository;
import com.uniti.cil.itta.inventory.device.support.FanTrayFixtures;
import com.uniti.cil.itta.inventory.device.validation.FanTrayValidator;
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
class FanTrayServiceImplTest {

    @Mock
    private FanTrayRepository repository;
    @Mock
    private EventPublisher events;

    private FanTrayServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(FanTrayFixtures.TENANT, "tester");
        service = new FanTrayServiceImpl(repository, new FanTrayMapper(), new FanTrayValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        FanTray entity = FanTrayFixtures.entity();
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(FanTrayFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(FanTrayFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(FanTrayFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        FanTrayCreateRequest request = FanTrayFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FanTrayFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(FanTray.class))).thenAnswer(inv -> inv.getArgument(0));
        FanTrayResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<FanTray> captor = ArgumentCaptor.forClass(FanTray.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(FanTrayFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        FanTrayCreateRequest request = FanTrayFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(FanTrayFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        FanTray entity = FanTrayFixtures.entity();
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(FanTray.class))).thenAnswer(inv -> inv.getArgument(0));
        FanTrayUpdateRequest request = new FanTrayUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        FanTrayResponse response = service.update(FanTrayFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        FanTray entity = FanTrayFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.of(entity));
        FanTrayUpdateRequest request = new FanTrayUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(FanTrayFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        FanTray entity = FanTrayFixtures.entity();
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(FanTrayFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        FanTray entity = FanTrayFixtures.entity();
        when(repository.findByTenantIdAndId(FanTrayFixtures.TENANT, FanTrayFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(FanTrayFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(FanTrayFixtures.entity())));
        assertThat(service.search(FanTrayFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        FanTrayCreateRequest ok = FanTrayFixtures.createRequest();
        FanTrayCreateRequest dup = FanTrayFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(FanTrayFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(FanTrayFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(FanTray.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<FanTrayResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(FanTrayFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(FanTrayFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(FanTrayFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
