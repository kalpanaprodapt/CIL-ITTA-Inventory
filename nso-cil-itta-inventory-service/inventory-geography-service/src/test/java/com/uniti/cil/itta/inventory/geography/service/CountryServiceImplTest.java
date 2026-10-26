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
import com.uniti.cil.itta.inventory.geography.mapper.CountryMapper;
import com.uniti.cil.itta.inventory.geography.model.Country;
import com.uniti.cil.itta.inventory.geography.repository.CountryRepository;
import com.uniti.cil.itta.inventory.geography.support.CountryFixtures;
import com.uniti.cil.itta.inventory.geography.validation.CountryValidator;
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
class CountryServiceImplTest {

    @Mock
    private CountryRepository repository;
    @Mock
    private EventPublisher events;

    private CountryServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CountryFixtures.TENANT, "tester");
        service = new CountryServiceImpl(repository, new CountryMapper(), new CountryValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Country entity = CountryFixtures.entity();
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CountryFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CountryFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CountryFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CountryCreateRequest request = CountryFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CountryFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Country.class))).thenAnswer(inv -> inv.getArgument(0));
        CountryResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CountryFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CountryCreateRequest request = CountryFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CountryFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Country entity = CountryFixtures.entity();
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Country.class))).thenAnswer(inv -> inv.getArgument(0));
        CountryUpdateRequest request = new CountryUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CountryResponse response = service.update(CountryFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Country entity = CountryFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.of(entity));
        CountryUpdateRequest request = new CountryUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CountryFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Country entity = CountryFixtures.entity();
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CountryFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Country entity = CountryFixtures.entity();
        when(repository.findByTenantIdAndId(CountryFixtures.TENANT, CountryFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CountryFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CountryFixtures.entity())));
        assertThat(service.search(CountryFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CountryCreateRequest ok = CountryFixtures.createRequest();
        CountryCreateRequest dup = CountryFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CountryFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CountryFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Country.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CountryResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CountryFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CountryFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CountryFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
