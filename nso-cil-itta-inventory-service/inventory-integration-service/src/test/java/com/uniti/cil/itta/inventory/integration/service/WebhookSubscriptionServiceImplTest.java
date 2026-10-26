package com.uniti.cil.itta.inventory.integration.service;

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
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.mapper.WebhookSubscriptionMapper;
import com.uniti.cil.itta.inventory.integration.model.WebhookSubscription;
import com.uniti.cil.itta.inventory.integration.repository.WebhookSubscriptionRepository;
import com.uniti.cil.itta.inventory.integration.support.WebhookSubscriptionFixtures;
import com.uniti.cil.itta.inventory.integration.validation.WebhookSubscriptionValidator;
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
class WebhookSubscriptionServiceImplTest {

    @Mock
    private WebhookSubscriptionRepository repository;
    @Mock
    private EventPublisher events;

    private WebhookSubscriptionServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(WebhookSubscriptionFixtures.TENANT, "tester");
        service = new WebhookSubscriptionServiceImpl(repository, new WebhookSubscriptionMapper(), new WebhookSubscriptionValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(WebhookSubscriptionFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(WebhookSubscriptionFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(WebhookSubscriptionFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        WebhookSubscriptionCreateRequest request = WebhookSubscriptionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WebhookSubscriptionFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(WebhookSubscription.class))).thenAnswer(inv -> inv.getArgument(0));
        WebhookSubscriptionResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<WebhookSubscription> captor = ArgumentCaptor.forClass(WebhookSubscription.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(WebhookSubscriptionFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        WebhookSubscriptionCreateRequest request = WebhookSubscriptionFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(WebhookSubscriptionFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(WebhookSubscription.class))).thenAnswer(inv -> inv.getArgument(0));
        WebhookSubscriptionUpdateRequest request = new WebhookSubscriptionUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        WebhookSubscriptionResponse response = service.update(WebhookSubscriptionFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.of(entity));
        WebhookSubscriptionUpdateRequest request = new WebhookSubscriptionUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(WebhookSubscriptionFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(WebhookSubscriptionFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        when(repository.findByTenantIdAndId(WebhookSubscriptionFixtures.TENANT, WebhookSubscriptionFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(WebhookSubscriptionFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(WebhookSubscriptionFixtures.entity())));
        assertThat(service.search(WebhookSubscriptionFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        WebhookSubscriptionCreateRequest ok = WebhookSubscriptionFixtures.createRequest();
        WebhookSubscriptionCreateRequest dup = WebhookSubscriptionFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(WebhookSubscriptionFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(WebhookSubscriptionFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(WebhookSubscription.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<WebhookSubscriptionResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(WebhookSubscriptionFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(WebhookSubscriptionFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(WebhookSubscriptionFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
