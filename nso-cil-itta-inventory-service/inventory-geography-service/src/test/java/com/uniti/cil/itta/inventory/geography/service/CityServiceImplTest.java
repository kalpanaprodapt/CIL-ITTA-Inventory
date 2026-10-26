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
import com.uniti.cil.itta.inventory.geography.mapper.CityMapper;
import com.uniti.cil.itta.inventory.geography.model.City;
import com.uniti.cil.itta.inventory.geography.repository.CityRepository;
import com.uniti.cil.itta.inventory.geography.support.CityFixtures;
import com.uniti.cil.itta.inventory.geography.validation.CityValidator;
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
class CityServiceImplTest {

    @Mock
    private CityRepository repository;
    @Mock
    private EventPublisher events;

    private CityServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CityFixtures.TENANT, "tester");
        service = new CityServiceImpl(repository, new CityMapper(), new CityValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        City entity = CityFixtures.entity();
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CityFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CityFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CityFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CityCreateRequest request = CityFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CityFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(City.class))).thenAnswer(inv -> inv.getArgument(0));
        CityResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CityFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CityCreateRequest request = CityFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CityFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        City entity = CityFixtures.entity();
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(City.class))).thenAnswer(inv -> inv.getArgument(0));
        CityUpdateRequest request = new CityUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CityResponse response = service.update(CityFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        City entity = CityFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.of(entity));
        CityUpdateRequest request = new CityUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CityFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        City entity = CityFixtures.entity();
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CityFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        City entity = CityFixtures.entity();
        when(repository.findByTenantIdAndId(CityFixtures.TENANT, CityFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CityFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CityFixtures.entity())));
        assertThat(service.search(CityFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CityCreateRequest ok = CityFixtures.createRequest();
        CityCreateRequest dup = CityFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CityFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CityFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(City.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CityResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CityFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CityFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CityFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
