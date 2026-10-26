package com.uniti.cil.itta.inventory.radio.service;

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
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.mapper.BackhaulLinkMapper;
import com.uniti.cil.itta.inventory.radio.model.BackhaulLink;
import com.uniti.cil.itta.inventory.radio.repository.BackhaulLinkRepository;
import com.uniti.cil.itta.inventory.radio.support.BackhaulLinkFixtures;
import com.uniti.cil.itta.inventory.radio.validation.BackhaulLinkValidator;
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
class BackhaulLinkServiceImplTest {

    @Mock
    private BackhaulLinkRepository repository;
    @Mock
    private EventPublisher events;

    private BackhaulLinkServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(BackhaulLinkFixtures.TENANT, "tester");
        service = new BackhaulLinkServiceImpl(repository, new BackhaulLinkMapper(), new BackhaulLinkValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(BackhaulLinkFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(BackhaulLinkFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(BackhaulLinkFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        BackhaulLinkCreateRequest request = BackhaulLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BackhaulLinkFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(BackhaulLink.class))).thenAnswer(inv -> inv.getArgument(0));
        BackhaulLinkResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<BackhaulLink> captor = ArgumentCaptor.forClass(BackhaulLink.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(BackhaulLinkFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        BackhaulLinkCreateRequest request = BackhaulLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(BackhaulLinkFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(BackhaulLink.class))).thenAnswer(inv -> inv.getArgument(0));
        BackhaulLinkUpdateRequest request = new BackhaulLinkUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        BackhaulLinkResponse response = service.update(BackhaulLinkFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.of(entity));
        BackhaulLinkUpdateRequest request = new BackhaulLinkUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(BackhaulLinkFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(BackhaulLinkFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        when(repository.findByTenantIdAndId(BackhaulLinkFixtures.TENANT, BackhaulLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(BackhaulLinkFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(BackhaulLinkFixtures.entity())));
        assertThat(service.search(BackhaulLinkFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        BackhaulLinkCreateRequest ok = BackhaulLinkFixtures.createRequest();
        BackhaulLinkCreateRequest dup = BackhaulLinkFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(BackhaulLinkFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(BackhaulLinkFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(BackhaulLink.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<BackhaulLinkResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(BackhaulLinkFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(BackhaulLinkFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(BackhaulLinkFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
