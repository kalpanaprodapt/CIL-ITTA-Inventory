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
import com.uniti.cil.itta.inventory.optical.mapper.OtnLinkMapper;
import com.uniti.cil.itta.inventory.optical.model.OtnLink;
import com.uniti.cil.itta.inventory.optical.repository.OtnLinkRepository;
import com.uniti.cil.itta.inventory.optical.support.OtnLinkFixtures;
import com.uniti.cil.itta.inventory.optical.validation.OtnLinkValidator;
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
class OtnLinkServiceImplTest {

    @Mock
    private OtnLinkRepository repository;
    @Mock
    private EventPublisher events;

    private OtnLinkServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(OtnLinkFixtures.TENANT, "tester");
        service = new OtnLinkServiceImpl(repository, new OtnLinkMapper(), new OtnLinkValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        OtnLink entity = OtnLinkFixtures.entity();
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(OtnLinkFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(OtnLinkFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(OtnLinkFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        OtnLinkCreateRequest request = OtnLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OtnLinkFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(OtnLink.class))).thenAnswer(inv -> inv.getArgument(0));
        OtnLinkResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<OtnLink> captor = ArgumentCaptor.forClass(OtnLink.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(OtnLinkFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        OtnLinkCreateRequest request = OtnLinkFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(OtnLinkFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        OtnLink entity = OtnLinkFixtures.entity();
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(OtnLink.class))).thenAnswer(inv -> inv.getArgument(0));
        OtnLinkUpdateRequest request = new OtnLinkUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        OtnLinkResponse response = service.update(OtnLinkFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        OtnLink entity = OtnLinkFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.of(entity));
        OtnLinkUpdateRequest request = new OtnLinkUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(OtnLinkFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        OtnLink entity = OtnLinkFixtures.entity();
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(OtnLinkFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        OtnLink entity = OtnLinkFixtures.entity();
        when(repository.findByTenantIdAndId(OtnLinkFixtures.TENANT, OtnLinkFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(OtnLinkFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(OtnLinkFixtures.entity())));
        assertThat(service.search(OtnLinkFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        OtnLinkCreateRequest ok = OtnLinkFixtures.createRequest();
        OtnLinkCreateRequest dup = OtnLinkFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(OtnLinkFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(OtnLinkFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(OtnLink.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<OtnLinkResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(OtnLinkFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OtnLinkFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(OtnLinkFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
