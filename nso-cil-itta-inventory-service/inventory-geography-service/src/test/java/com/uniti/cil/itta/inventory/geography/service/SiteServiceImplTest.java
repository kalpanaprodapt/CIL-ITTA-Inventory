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
import com.uniti.cil.itta.inventory.geography.mapper.SiteMapper;
import com.uniti.cil.itta.inventory.geography.model.Site;
import com.uniti.cil.itta.inventory.geography.repository.SiteRepository;
import com.uniti.cil.itta.inventory.geography.support.SiteFixtures;
import com.uniti.cil.itta.inventory.geography.validation.SiteValidator;
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
class SiteServiceImplTest {

    @Mock
    private SiteRepository repository;
    @Mock
    private EventPublisher events;

    private SiteServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SiteFixtures.TENANT, "tester");
        service = new SiteServiceImpl(repository, new SiteMapper(), new SiteValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Site entity = SiteFixtures.entity();
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SiteFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SiteFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SiteFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SiteCreateRequest request = SiteFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SiteFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Site.class))).thenAnswer(inv -> inv.getArgument(0));
        SiteResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Site> captor = ArgumentCaptor.forClass(Site.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SiteFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SiteCreateRequest request = SiteFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SiteFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Site entity = SiteFixtures.entity();
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Site.class))).thenAnswer(inv -> inv.getArgument(0));
        SiteUpdateRequest request = new SiteUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SiteResponse response = service.update(SiteFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Site entity = SiteFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.of(entity));
        SiteUpdateRequest request = new SiteUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SiteFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Site entity = SiteFixtures.entity();
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SiteFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Site entity = SiteFixtures.entity();
        when(repository.findByTenantIdAndId(SiteFixtures.TENANT, SiteFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SiteFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SiteFixtures.entity())));
        assertThat(service.search(SiteFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SiteCreateRequest ok = SiteFixtures.createRequest();
        SiteCreateRequest dup = SiteFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SiteFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SiteFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Site.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SiteResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SiteFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SiteFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SiteFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
