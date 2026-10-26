package com.uniti.cil.itta.inventory.ipam.service;

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
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.mapper.DnsRecordMapper;
import com.uniti.cil.itta.inventory.ipam.model.DnsRecord;
import com.uniti.cil.itta.inventory.ipam.repository.DnsRecordRepository;
import com.uniti.cil.itta.inventory.ipam.support.DnsRecordFixtures;
import com.uniti.cil.itta.inventory.ipam.validation.DnsRecordValidator;
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
class DnsRecordServiceImplTest {

    @Mock
    private DnsRecordRepository repository;
    @Mock
    private EventPublisher events;

    private DnsRecordServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(DnsRecordFixtures.TENANT, "tester");
        service = new DnsRecordServiceImpl(repository, new DnsRecordMapper(), new DnsRecordValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        DnsRecord entity = DnsRecordFixtures.entity();
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(DnsRecordFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(DnsRecordFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(DnsRecordFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        DnsRecordCreateRequest request = DnsRecordFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DnsRecordFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(DnsRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        DnsRecordResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<DnsRecord> captor = ArgumentCaptor.forClass(DnsRecord.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(DnsRecordFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        DnsRecordCreateRequest request = DnsRecordFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(DnsRecordFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        DnsRecord entity = DnsRecordFixtures.entity();
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(DnsRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        DnsRecordUpdateRequest request = new DnsRecordUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        DnsRecordResponse response = service.update(DnsRecordFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        DnsRecord entity = DnsRecordFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.of(entity));
        DnsRecordUpdateRequest request = new DnsRecordUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(DnsRecordFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        DnsRecord entity = DnsRecordFixtures.entity();
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(DnsRecordFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        DnsRecord entity = DnsRecordFixtures.entity();
        when(repository.findByTenantIdAndId(DnsRecordFixtures.TENANT, DnsRecordFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(DnsRecordFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(DnsRecordFixtures.entity())));
        assertThat(service.search(DnsRecordFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        DnsRecordCreateRequest ok = DnsRecordFixtures.createRequest();
        DnsRecordCreateRequest dup = DnsRecordFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(DnsRecordFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(DnsRecordFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(DnsRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<DnsRecordResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(DnsRecordFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(DnsRecordFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(DnsRecordFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
