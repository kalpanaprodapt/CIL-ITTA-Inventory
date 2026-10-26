package com.uniti.cil.itta.inventory.topology.service;

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
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.mapper.LinkMapper;
import com.uniti.cil.itta.inventory.topology.model.Link;
import com.uniti.cil.itta.inventory.topology.repository.LinkRepository;
import com.uniti.cil.itta.inventory.topology.support.LinkFixtures;
import com.uniti.cil.itta.inventory.topology.validation.LinkValidator;
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
class LinkServiceImplTest {

    @Mock
    private LinkRepository repository;
    @Mock
    private EventPublisher events;

    private LinkServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(LinkFixtures.TENANT, "tester");
        service = new LinkServiceImpl(repository, new LinkMapper(), new LinkValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Link entity = LinkFixtures.entity();
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(LinkFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(LinkFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(LinkFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        LinkCreateRequest request = LinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LinkFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Link.class))).thenAnswer(inv -> inv.getArgument(0));
        LinkResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Link> captor = ArgumentCaptor.forClass(Link.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(LinkFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        LinkCreateRequest request = LinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LinkFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Link entity = LinkFixtures.entity();
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Link.class))).thenAnswer(inv -> inv.getArgument(0));
        LinkUpdateRequest request = new LinkUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        LinkResponse response = service.update(LinkFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Link entity = LinkFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.of(entity));
        LinkUpdateRequest request = new LinkUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(LinkFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Link entity = LinkFixtures.entity();
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(LinkFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Link entity = LinkFixtures.entity();
        when(repository.findByTenantIdAndId(LinkFixtures.TENANT, LinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(LinkFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(LinkFixtures.entity())));
        assertThat(service.search(LinkFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        LinkCreateRequest ok = LinkFixtures.createRequest();
        LinkCreateRequest dup = LinkFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(LinkFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(LinkFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Link.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<LinkResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(LinkFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(LinkFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(LinkFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
