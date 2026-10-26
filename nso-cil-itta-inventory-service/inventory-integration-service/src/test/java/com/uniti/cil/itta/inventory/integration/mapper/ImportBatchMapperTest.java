package com.uniti.cil.itta.inventory.integration.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.ImportBatchFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ImportBatchMapperTest {

    private final ImportBatchMapper mapper = new ImportBatchMapper();

    @Test
    void toEntityCopiesEveryField() {
        ImportBatchCreateRequest request = ImportBatchFixtures.createRequest();
        ImportBatch entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getFileName()).isEqualTo(request.getFileName());
        assertThat(entity.getEntityType()).isEqualTo(request.getEntityType());
        assertThat(entity.getTotalRows()).isEqualTo(request.getTotalRows());
        assertThat(entity.getProcessedRows()).isEqualTo(request.getProcessedRows());
        assertThat(entity.getFailedRows()).isEqualTo(request.getFailedRows());
        assertThat(entity.getBatchState()).isEqualTo(request.getBatchState());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        ImportBatch entity = ImportBatchFixtures.entity();
        ImportBatchResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getFileName()).isEqualTo(entity.getFileName());
        assertThat(response.getEntityType()).isEqualTo(entity.getEntityType());
        assertThat(response.getTotalRows()).isEqualTo(entity.getTotalRows());
        assertThat(response.getProcessedRows()).isEqualTo(entity.getProcessedRows());
        assertThat(response.getFailedRows()).isEqualTo(entity.getFailedRows());
        assertThat(response.getBatchState()).isEqualTo(entity.getBatchState());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        ImportBatch entity = ImportBatchFixtures.entity();
        ImportBatchSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ImportBatchFixtures.entity())).hasSameSizeAs(ImportBatchMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ImportBatch entity = ImportBatchFixtures.entity();
        ImportBatchUpdateRequest request = new ImportBatchUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setFileName("fileName-3");
        request.setEntityType("entityType-3");
        request.setTotalRows(13);
        request.setProcessedRows(13);
        request.setFailedRows(13);
        request.setBatchState(ImportBatchBatchState.LOADING);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(ImportBatchLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getFileName()).isEqualTo("fileName-3");
        assertThat(entity.getEntityType()).isEqualTo("entityType-3");
        assertThat(entity.getTotalRows()).isEqualTo(13);
        assertThat(entity.getProcessedRows()).isEqualTo(13);
        assertThat(entity.getFailedRows()).isEqualTo(13);
        assertThat(entity.getBatchState()).isEqualTo(ImportBatchBatchState.LOADING);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ImportBatchLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ImportBatch entity = ImportBatchFixtures.entity();
        ImportBatch before = ImportBatchFixtures.entity();
        mapper.applyUpdate(new ImportBatchUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
