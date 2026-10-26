package com.uniti.cil.itta.inventory.customer.service;

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
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.mapper.SlaProfileMapper;
import com.uniti.cil.itta.inventory.customer.model.SlaProfile;
import com.uniti.cil.itta.inventory.customer.repository.SlaProfileRepository;
import com.uniti.cil.itta.inventory.customer.support.SlaProfileFixtures;
import com.uniti.cil.itta.inventory.customer.validation.SlaProfileValidator;
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
class SlaProfileServiceImplTest {

    @Mock
    private SlaProfileRepository repository;
    @Mock
    private EventPublisher events;

    private SlaProfileServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(SlaProfileFixtures.TENANT, "tester");
        service = new SlaProfileServiceImpl(repository, new SlaProfileMapper(), new SlaProfileValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        SlaProfile entity = SlaProfileFixtures.entity();
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(SlaProfileFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(SlaProfileFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(SlaProfileFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        SlaProfileCreateRequest request = SlaProfileFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SlaProfileFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(SlaProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        SlaProfileResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<SlaProfile> captor = ArgumentCaptor.forClass(SlaProfile.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(SlaProfileFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        SlaProfileCreateRequest request = SlaProfileFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(SlaProfileFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        SlaProfile entity = SlaProfileFixtures.entity();
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(SlaProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        SlaProfileUpdateRequest request = new SlaProfileUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        SlaProfileResponse response = service.update(SlaProfileFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        SlaProfile entity = SlaProfileFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.of(entity));
        SlaProfileUpdateRequest request = new SlaProfileUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(SlaProfileFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        SlaProfile entity = SlaProfileFixtures.entity();
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(SlaProfileFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        SlaProfile entity = SlaProfileFixtures.entity();
        when(repository.findByTenantIdAndId(SlaProfileFixtures.TENANT, SlaProfileFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(SlaProfileFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(SlaProfileFixtures.entity())));
        assertThat(service.search(SlaProfileFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        SlaProfileCreateRequest ok = SlaProfileFixtures.createRequest();
        SlaProfileCreateRequest dup = SlaProfileFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(SlaProfileFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(SlaProfileFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(SlaProfile.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<SlaProfileResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(SlaProfileFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(SlaProfileFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(SlaProfileFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
