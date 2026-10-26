package com.uniti.cil.itta.inventory.virtual.service;

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
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.mapper.CloudAccountMapper;
import com.uniti.cil.itta.inventory.virtual.model.CloudAccount;
import com.uniti.cil.itta.inventory.virtual.repository.CloudAccountRepository;
import com.uniti.cil.itta.inventory.virtual.support.CloudAccountFixtures;
import com.uniti.cil.itta.inventory.virtual.validation.CloudAccountValidator;
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
class CloudAccountServiceImplTest {

    @Mock
    private CloudAccountRepository repository;
    @Mock
    private EventPublisher events;

    private CloudAccountServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(CloudAccountFixtures.TENANT, "tester");
        service = new CloudAccountServiceImpl(repository, new CloudAccountMapper(), new CloudAccountValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        CloudAccount entity = CloudAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(CloudAccountFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(CloudAccountFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(CloudAccountFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        CloudAccountCreateRequest request = CloudAccountFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CloudAccountFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(CloudAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        CloudAccountResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<CloudAccount> captor = ArgumentCaptor.forClass(CloudAccount.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(CloudAccountFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        CloudAccountCreateRequest request = CloudAccountFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(CloudAccountFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        CloudAccount entity = CloudAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CloudAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        CloudAccountUpdateRequest request = new CloudAccountUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        CloudAccountResponse response = service.update(CloudAccountFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        CloudAccount entity = CloudAccountFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.of(entity));
        CloudAccountUpdateRequest request = new CloudAccountUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(CloudAccountFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        CloudAccount entity = CloudAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(CloudAccountFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        CloudAccount entity = CloudAccountFixtures.entity();
        when(repository.findByTenantIdAndId(CloudAccountFixtures.TENANT, CloudAccountFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(CloudAccountFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(CloudAccountFixtures.entity())));
        assertThat(service.search(CloudAccountFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        CloudAccountCreateRequest ok = CloudAccountFixtures.createRequest();
        CloudAccountCreateRequest dup = CloudAccountFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(CloudAccountFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(CloudAccountFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(CloudAccount.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<CloudAccountResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(CloudAccountFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(CloudAccountFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(CloudAccountFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
