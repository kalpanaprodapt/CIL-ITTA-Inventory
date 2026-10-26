package com.uniti.cil.itta.inventory.device.service;

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
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.mapper.LineCardMapper;
import com.uniti.cil.itta.inventory.device.model.LineCard;
import com.uniti.cil.itta.inventory.device.repository.LineCardRepository;
import com.uniti.cil.itta.inventory.device.support.LineCardFixtures;
import com.uniti.cil.itta.inventory.device.validation.LineCardValidator;
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
class LineCardServiceImplTest {

    @Mock
    private LineCardRepository repository;
    @Mock
    private EventPublisher events;

    private LineCardServiceImpl service;

    @BeforeEach
    void setUp() {
        TenantContext.set(LineCardFixtures.TENANT, "tester");
        service = new LineCardServiceImpl(repository, new LineCardMapper(), new LineCardValidator(), events);
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getReturnsMappedResponse() {
        LineCard entity = LineCardFixtures.entity();
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.of(entity));
        assertThat(service.get(LineCardFixtures.ID).getCode()).isEqualTo(entity.getCode());
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(LineCardFixtures.ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByCodeThrowsWhenMissing() {
        when(repository.findByTenantIdAndCode(LineCardFixtures.TENANT, "nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByCode("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createPersistsAndPublishes() {
        LineCardCreateRequest request = LineCardFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LineCardFixtures.TENANT, request.getCode())).thenReturn(false);
        when(repository.save(any(LineCard.class))).thenAnswer(inv -> inv.getArgument(0));
        LineCardResponse response = service.create(request);
        assertThat(response.getCode()).isEqualTo(request.getCode());
        ArgumentCaptor<LineCard> captor = ArgumentCaptor.forClass(LineCard.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo(LineCardFixtures.TENANT);
        verify(events).publish(any());
    }

    @Test
    void createRejectsDuplicateCode() {
        LineCardCreateRequest request = LineCardFixtures.createRequest();
        when(repository.existsByTenantIdAndCode(LineCardFixtures.TENANT, request.getCode())).thenReturn(true);
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void updateAppliesChangesAndPublishesStatusChange() {
        LineCard entity = LineCardFixtures.entity();
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(LineCard.class))).thenAnswer(inv -> inv.getArgument(0));
        LineCardUpdateRequest request = new LineCardUpdateRequest();
        request.setName("Renamed");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        LineCardResponse response = service.update(LineCardFixtures.ID, request);
        assertThat(response.getName()).isEqualTo("Renamed");
        assertThat(response.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        verify(events, org.mockito.Mockito.times(2)).publish(any());
    }

    @Test
    void updateRejectsStaleVersion() {
        LineCard entity = LineCardFixtures.entity();
        entity.setVersion(5L);
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.of(entity));
        LineCardUpdateRequest request = new LineCardUpdateRequest();
        request.setExpectedVersion(2L);
        assertThatThrownBy(() -> service.update(LineCardFixtures.ID, request))
                .isInstanceOf(org.springframework.dao.OptimisticLockingFailureException.class);
    }

    @Test
    void changeStatusIsNoOpWhenUnchanged() {
        LineCard entity = LineCardFixtures.entity();
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.of(entity));
        service.changeStatus(LineCardFixtures.ID, LifecycleStatus.ACTIVE);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteRemovesAndPublishes() {
        LineCard entity = LineCardFixtures.entity();
        when(repository.findByTenantIdAndId(LineCardFixtures.TENANT, LineCardFixtures.ID)).thenReturn(Optional.of(entity));
        service.delete(LineCardFixtures.ID);
        verify(repository).delete(entity);
        verify(events).publish(any());
    }

    @Test
    void searchDelegatesToRepository() {
        when(repository.findAll(any(Specification.class), eq(PageRequest.of(0, 20))))
                .thenReturn(new PageImpl<>(List.of(LineCardFixtures.entity())));
        assertThat(service.search(LineCardFixtures.criteria(), PageRequest.of(0, 20)).getContent()).hasSize(1);
    }

    @Test
    void bulkCreateCollectsFailures() {
        LineCardCreateRequest ok = LineCardFixtures.createRequest();
        LineCardCreateRequest dup = LineCardFixtures.createRequest();
        dup.setCode("DUP");
        when(repository.existsByTenantIdAndCode(LineCardFixtures.TENANT, ok.getCode())).thenReturn(false);
        when(repository.existsByTenantIdAndCode(LineCardFixtures.TENANT, "DUP")).thenReturn(true);
        when(repository.save(any(LineCard.class))).thenAnswer(inv -> inv.getArgument(0));
        BulkResult<LineCardResponse> result = service.bulkCreate(List.of(ok, dup));
        assertThat(result.getSucceeded()).hasSize(1);
        assertThat(result.getFailures()).hasSize(1);
        assertThat(result.getFailures().get(0).getIndex()).isEqualTo(1);
    }

    @Test
    void countByStatusFillsMissingBuckets() {
        when(repository.countGroupedByStatus(LineCardFixtures.TENANT))
                .thenReturn(java.util.Collections.singletonList(new Object[] {LifecycleStatus.ACTIVE, 3L}));
        var counts = service.countByStatus();
        assertThat(counts.get(LifecycleStatus.ACTIVE)).isEqualTo(3L);
        assertThat(counts.get(LifecycleStatus.PLANNED)).isZero();
    }

    @Test
    void exportCsvWritesHeaderAndRows() {
        when(repository.findAll(any(Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(LineCardFixtures.entity())));
        java.io.StringWriter out = new java.io.StringWriter();
        service.exportCsv(LineCardFixtures.criteria(), out);
        assertThat(out.toString().split("\\R")).hasSize(2);
    }
}
