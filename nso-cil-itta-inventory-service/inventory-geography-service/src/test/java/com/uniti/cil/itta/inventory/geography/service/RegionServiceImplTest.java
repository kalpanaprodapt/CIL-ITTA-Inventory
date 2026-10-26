package com.uniti.cil.itta.inventory.geography.service;

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
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.mapper.RegionMapper;
import com.uniti.cil.itta.inventory.geography.model.Region;
import com.uniti.cil.itta.inventory.geography.repository.RegionRepository;
import com.uniti.cil.itta.inventory.geography.support.RegionFixtures;
import com.uniti.cil.itta.inventory.geography.validation.RegionValidator;
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
class RegionServiceImplTest {

    @Mock
    private RegionRepository repository;
    @Mock
    private EventPublisher events;

    private RegionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(RegionFixtures.TENANT, "tester");
        service = new RegionServiceImpl(repository, new RegionMapper(), new RegionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Region entity = RegionFixtures.entity();
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(RegionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(RegionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(RegionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        RegionCreateRequest request = RegionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RegionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Region.class))).thenAnswer(inv -> inv.getArgument(0));
        RegionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Region> captor = ArgumentCaptor.forClass(Region.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(RegionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        RegionCreateRequest request = RegionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(RegionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Region entity = RegionFixtures.entity();
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Region.class))).thenAnswer(inv -> inv.getArgument(0));
        RegionUpdateRequest request = new RegionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        RegionResponse response = service.update(RegionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Region entity = RegionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.of(entity));
        RegionUpdateRequest request = new RegionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(RegionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Region entity = RegionFixtures.entity();
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(RegionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Region entity = RegionFixtures.entity();
        when(repository.findByTenantIdAndId(RegionFixtures.TENANT, RegionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(RegionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(RegionFixtures.entity())));
        assertThat(service.search(RegionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        RegionCreateRequest ok = RegionFixtures.createRequest();
        RegionCreateRequest dup = RegionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(RegionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(RegionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Region.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<RegionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(RegionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(RegionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(RegionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
