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
import com.uniti.cil.itta.inventory.optical.mapper.AmplifierMapper;
import com.uniti.cil.itta.inventory.optical.model.Amplifier;
import com.uniti.cil.itta.inventory.optical.repository.AmplifierRepository;
import com.uniti.cil.itta.inventory.optical.support.AmplifierFixtures;
import com.uniti.cil.itta.inventory.optical.validation.AmplifierValidator;
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
class AmplifierServiceImplTest {

    @Mock
    private AmplifierRepository repository;
    @Mock
    private EventPublisher events;

    private AmplifierServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(AmplifierFixtures.TENANT, "tester");
        service = new AmplifierServiceImpl(repository, new AmplifierMapper(), new AmplifierValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        Amplifier entity = AmplifierFixtures.entity();
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(AmplifierFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(AmplifierFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(AmplifierFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        AmplifierCreateRequest request = AmplifierFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AmplifierFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(Amplifier.class))).thenAnswer(inv -> inv.getArgument(0));
        AmplifierResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<Amplifier> captor = ArgumentCaptor.forClass(Amplifier.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(AmplifierFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        AmplifierCreateRequest request = AmplifierFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(AmplifierFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        Amplifier entity = AmplifierFixtures.entity();
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Amplifier.class))).thenAnswer(inv -> inv.getArgument(0));
        AmplifierUpdateRequest request = new AmplifierUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        AmplifierResponse response = service.update(AmplifierFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        Amplifier entity = AmplifierFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.of(entity));
        AmplifierUpdateRequest request = new AmplifierUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(AmplifierFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        Amplifier entity = AmplifierFixtures.entity();
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(AmplifierFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        Amplifier entity = AmplifierFixtures.entity();
        when(repository.findByTenantIdAndId(AmplifierFixtures.TENANT, AmplifierFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(AmplifierFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(AmplifierFixtures.entity())));
        assertThat(service.search(AmplifierFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        AmplifierCreateRequest ok = AmplifierFixtures.createRequest();
        AmplifierCreateRequest dup = AmplifierFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(AmplifierFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(AmplifierFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(Amplifier.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<AmplifierResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(AmplifierFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AmplifierFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(AmplifierFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
