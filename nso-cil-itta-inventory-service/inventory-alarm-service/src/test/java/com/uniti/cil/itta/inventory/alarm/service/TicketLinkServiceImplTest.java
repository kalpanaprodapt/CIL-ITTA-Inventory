package com.uniti.cil.itta.inventory.alarm.service;

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
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.mapper.TicketLinkMapper;
import com.uniti.cil.itta.inventory.alarm.model.TicketLink;
import com.uniti.cil.itta.inventory.alarm.repository.TicketLinkRepository;
import com.uniti.cil.itta.inventory.alarm.support.TicketLinkFixtures;
import com.uniti.cil.itta.inventory.alarm.validation.TicketLinkValidator;
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
class TicketLinkServiceImplTest {

    @Mock
    private TicketLinkRepository repository;
    @Mock
    private EventPublisher events;

    private TicketLinkServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(TicketLinkFixtures.TENANT, "tester");
        service = new TicketLinkServiceImpl(repository, new TicketLinkMapper(), new TicketLinkValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        TicketLink entity = TicketLinkFixtures.entity();
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(TicketLinkFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(TicketLinkFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(TicketLinkFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        TicketLinkCreateRequest request = TicketLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TicketLinkFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(TicketLink.class))).thenAnswer(inv -> inv.getArgument(0));
        TicketLinkResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<TicketLink> captor = ArgumentCaptor.forClass(TicketLink.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(TicketLinkFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        TicketLinkCreateRequest request = TicketLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(TicketLinkFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        TicketLink entity = TicketLinkFixtures.entity();
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(TicketLink.class))).thenAnswer(inv -> inv.getArgument(0));
        TicketLinkUpdateRequest request = new TicketLinkUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        TicketLinkResponse response = service.update(TicketLinkFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        TicketLink entity = TicketLinkFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.of(entity));
        TicketLinkUpdateRequest request = new TicketLinkUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(TicketLinkFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        TicketLink entity = TicketLinkFixtures.entity();
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(TicketLinkFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        TicketLink entity = TicketLinkFixtures.entity();
        when(repository.findByTenantIdAndId(TicketLinkFixtures.TENANT, TicketLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(TicketLinkFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(TicketLinkFixtures.entity())));
        assertThat(service.search(TicketLinkFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        TicketLinkCreateRequest ok = TicketLinkFixtures.createRequest();
        TicketLinkCreateRequest dup = TicketLinkFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(TicketLinkFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(TicketLinkFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(TicketLink.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<TicketLinkResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(TicketLinkFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TicketLinkFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(TicketLinkFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
