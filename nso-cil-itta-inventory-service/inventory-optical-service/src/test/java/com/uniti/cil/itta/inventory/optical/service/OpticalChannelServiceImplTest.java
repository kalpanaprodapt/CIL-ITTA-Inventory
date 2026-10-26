package com.uniti.cil.itta.inventory.optical.service;

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
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.mapper.OpticalChannelMapper;
import com.uniti.cil.itta.inventory.optical.model.OpticalChannel;
import com.uniti.cil.itta.inventory.optical.repository.OpticalChannelRepository;
import com.uniti.cil.itta.inventory.optical.support.OpticalChannelFixtures;
import com.uniti.cil.itta.inventory.optical.validation.OpticalChannelValidator;
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
class OpticalChannelServiceImplTest {

    @Mock
    private OpticalChannelRepository repository;
    @Mock
    private EventPublisher events;

    private OpticalChannelServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(OpticalChannelFixtures.TENANT, "tester");
        service = new OpticalChannelServiceImpl(repository, new OpticalChannelMapper(), new OpticalChannelValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(OpticalChannelFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(OpticalChannelFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(OpticalChannelFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        OpticalChannelCreateRequest request = OpticalChannelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OpticalChannelFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(OpticalChannel.class))).thenAnswer(inv -> inv.getArgument(0));
        OpticalChannelResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<OpticalChannel> captor = ArgumentCaptor.forClass(OpticalChannel.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(OpticalChannelFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        OpticalChannelCreateRequest request = OpticalChannelFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OpticalChannelFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(OpticalChannel.class))).thenAnswer(inv -> inv.getArgument(0));
        OpticalChannelUpdateRequest request = new OpticalChannelUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        OpticalChannelResponse response = service.update(OpticalChannelFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.of(entity));
        OpticalChannelUpdateRequest request = new OpticalChannelUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(OpticalChannelFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(OpticalChannelFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        when(repository.findByTenantIdAndId(OpticalChannelFixtures.TENANT, OpticalChannelFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(OpticalChannelFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(OpticalChannelFixtures.entity())));
        assertThat(service.search(OpticalChannelFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        OpticalChannelCreateRequest ok = OpticalChannelFixtures.createRequest();
        OpticalChannelCreateRequest dup = OpticalChannelFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(OpticalChannelFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(OpticalChannelFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(OpticalChannel.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<OpticalChannelResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(OpticalChannelFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OpticalChannelFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(OpticalChannelFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
