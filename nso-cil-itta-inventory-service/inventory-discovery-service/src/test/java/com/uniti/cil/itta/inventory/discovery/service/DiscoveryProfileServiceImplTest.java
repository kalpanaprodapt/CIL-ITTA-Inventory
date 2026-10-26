package com.uniti.cil.itta.inventory.discovery.service;

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
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveryProfileMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryProfile;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveryProfileRepository;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryProfileFixtures;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveryProfileValidator;
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
class DiscoveryProfileServiceImplTest {

    @Mock
    private DiscoveryProfileRepository repository;
    @Mock
    private EventPublisher events;

    private DiscoveryProfileServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(DiscoveryProfileFixtures.TENANT, "tester");
        service = new DiscoveryProfileServiceImpl(repository, new DiscoveryProfileMapper(), new DiscoveryProfileValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(DiscoveryProfileFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(DiscoveryProfileFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(DiscoveryProfileFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        DiscoveryProfileCreateRequest request = DiscoveryProfileFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveryProfileFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(DiscoveryProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveryProfileResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<DiscoveryProfile> captor = ArgumentCaptor.forClass(DiscoveryProfile.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(DiscoveryProfileFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        DiscoveryProfileCreateRequest request = DiscoveryProfileFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DiscoveryProfileFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(DiscoveryProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        DiscoveryProfileUpdateRequest request = new DiscoveryProfileUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        DiscoveryProfileResponse response = service.update(DiscoveryProfileFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.of(entity));
        DiscoveryProfileUpdateRequest request = new DiscoveryProfileUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(DiscoveryProfileFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(DiscoveryProfileFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        when(repository.findByTenantIdAndId(DiscoveryProfileFixtures.TENANT, DiscoveryProfileFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(DiscoveryProfileFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(DiscoveryProfileFixtures.entity())));
        assertThat(service.search(DiscoveryProfileFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        DiscoveryProfileCreateRequest ok = DiscoveryProfileFixtures.createRequest();
        DiscoveryProfileCreateRequest dup = DiscoveryProfileFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(DiscoveryProfileFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(DiscoveryProfileFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(DiscoveryProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<DiscoveryProfileResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(DiscoveryProfileFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(DiscoveryProfileFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(DiscoveryProfileFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
