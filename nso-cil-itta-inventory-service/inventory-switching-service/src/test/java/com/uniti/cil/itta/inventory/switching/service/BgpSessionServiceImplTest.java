package com.uniti.cil.itta.inventory.switching.service;

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
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.mapper.BgpSessionMapper;
import com.uniti.cil.itta.inventory.switching.model.BgpSession;
import com.uniti.cil.itta.inventory.switching.repository.BgpSessionRepository;
import com.uniti.cil.itta.inventory.switching.support.BgpSessionFixtures;
import com.uniti.cil.itta.inventory.switching.validation.BgpSessionValidator;
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
class BgpSessionServiceImplTest {

    @Mock
    private BgpSessionRepository repository;
    @Mock
    private EventPublisher events;

    private BgpSessionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(BgpSessionFixtures.TENANT, "tester");
        service = new BgpSessionServiceImpl(repository, new BgpSessionMapper(), new BgpSessionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        BgpSession entity = BgpSessionFixtures.entity();
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(BgpSessionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(BgpSessionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(BgpSessionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        BgpSessionCreateRequest request = BgpSessionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BgpSessionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(BgpSession.class))).thenAnswer(inv -> inv.getArgument(0));
        BgpSessionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<BgpSession> captor = ArgumentCaptor.forClass(BgpSession.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(BgpSessionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        BgpSessionCreateRequest request = BgpSessionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BgpSessionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        BgpSession entity = BgpSessionFixtures.entity();
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(BgpSession.class))).thenAnswer(inv -> inv.getArgument(0));
        BgpSessionUpdateRequest request = new BgpSessionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        BgpSessionResponse response = service.update(BgpSessionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        BgpSession entity = BgpSessionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.of(entity));
        BgpSessionUpdateRequest request = new BgpSessionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(BgpSessionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        BgpSession entity = BgpSessionFixtures.entity();
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(BgpSessionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        BgpSession entity = BgpSessionFixtures.entity();
        when(repository.findByTenantIdAndId(BgpSessionFixtures.TENANT, BgpSessionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(BgpSessionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(BgpSessionFixtures.entity())));
        assertThat(service.search(BgpSessionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        BgpSessionCreateRequest ok = BgpSessionFixtures.createRequest();
        BgpSessionCreateRequest dup = BgpSessionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(BgpSessionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(BgpSessionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(BgpSession.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<BgpSessionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(BgpSessionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(BgpSessionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(BgpSessionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
