package com.uniti.cil.itta.inventory.facility.service;

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
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.mapper.PowerFeedMapper;
import com.uniti.cil.itta.inventory.facility.model.PowerFeed;
import com.uniti.cil.itta.inventory.facility.repository.PowerFeedRepository;
import com.uniti.cil.itta.inventory.facility.support.PowerFeedFixtures;
import com.uniti.cil.itta.inventory.facility.validation.PowerFeedValidator;
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
class PowerFeedServiceImplTest {

    @Mock
    private PowerFeedRepository repository;
    @Mock
    private EventPublisher events;

    private PowerFeedServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(PowerFeedFixtures.TENANT, "tester");
        service = new PowerFeedServiceImpl(repository, new PowerFeedMapper(), new PowerFeedValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        PowerFeed entity = PowerFeedFixtures.entity();
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(PowerFeedFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(PowerFeedFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(PowerFeedFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        PowerFeedCreateRequest request = PowerFeedFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PowerFeedFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(PowerFeed.class))).thenAnswer(inv -> inv.getArgument(0));
        PowerFeedResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<PowerFeed> captor = ArgumentCaptor.forClass(PowerFeed.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(PowerFeedFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        PowerFeedCreateRequest request = PowerFeedFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(PowerFeedFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        PowerFeed entity = PowerFeedFixtures.entity();
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(PowerFeed.class))).thenAnswer(inv -> inv.getArgument(0));
        PowerFeedUpdateRequest request = new PowerFeedUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        PowerFeedResponse response = service.update(PowerFeedFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        PowerFeed entity = PowerFeedFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.of(entity));
        PowerFeedUpdateRequest request = new PowerFeedUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(PowerFeedFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        PowerFeed entity = PowerFeedFixtures.entity();
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(PowerFeedFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        PowerFeed entity = PowerFeedFixtures.entity();
        when(repository.findByTenantIdAndId(PowerFeedFixtures.TENANT, PowerFeedFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(PowerFeedFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(PowerFeedFixtures.entity())));
        assertThat(service.search(PowerFeedFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        PowerFeedCreateRequest ok = PowerFeedFixtures.createRequest();
        PowerFeedCreateRequest dup = PowerFeedFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(PowerFeedFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(PowerFeedFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(PowerFeed.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<PowerFeedResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(PowerFeedFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(PowerFeedFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(PowerFeedFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
