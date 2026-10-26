package com.uniti.cil.itta.inventory.mpls.service;

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
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.mapper.VpnServiceMapper;
import com.uniti.cil.itta.inventory.mpls.model.VpnService;
import com.uniti.cil.itta.inventory.mpls.repository.VpnServiceRepository;
import com.uniti.cil.itta.inventory.mpls.support.VpnServiceFixtures;
import com.uniti.cil.itta.inventory.mpls.validation.VpnServiceValidator;
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
class VpnServiceServiceImplTest {

    @Mock
    private VpnServiceRepository repository;
    @Mock
    private EventPublisher events;

    private VpnServiceServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(VpnServiceFixtures.TENANT, "tester");
        service = new VpnServiceServiceImpl(repository, new VpnServiceMapper(), new VpnServiceValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        VpnService entity = VpnServiceFixtures.entity();
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(VpnServiceFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(VpnServiceFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(VpnServiceFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        VpnServiceCreateRequest request = VpnServiceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VpnServiceFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(VpnService.class))).thenAnswer(inv -> inv.getArgument(0));
        VpnServiceResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<VpnService> captor = ArgumentCaptor.forClass(VpnService.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(VpnServiceFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        VpnServiceCreateRequest request = VpnServiceFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(VpnServiceFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        VpnService entity = VpnServiceFixtures.entity();
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(VpnService.class))).thenAnswer(inv -> inv.getArgument(0));
        VpnServiceUpdateRequest request = new VpnServiceUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        VpnServiceResponse response = service.update(VpnServiceFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        VpnService entity = VpnServiceFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.of(entity));
        VpnServiceUpdateRequest request = new VpnServiceUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(VpnServiceFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        VpnService entity = VpnServiceFixtures.entity();
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(VpnServiceFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        VpnService entity = VpnServiceFixtures.entity();
        when(repository.findByTenantIdAndId(VpnServiceFixtures.TENANT, VpnServiceFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(VpnServiceFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(VpnServiceFixtures.entity())));
        assertThat(service.search(VpnServiceFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        VpnServiceCreateRequest ok = VpnServiceFixtures.createRequest();
        VpnServiceCreateRequest dup = VpnServiceFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(VpnServiceFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(VpnServiceFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(VpnService.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<VpnServiceResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(VpnServiceFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(VpnServiceFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(VpnServiceFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
