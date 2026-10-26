package com.uniti.cil.itta.inventory.optical.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.WdmSystemFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WdmSystemMapperTest {

    private final WdmSystemMapper mapper = new WdmSystemMapper();

    @Test
    void toEntityCopiesEveryField() {
        WdmSystemCreateRequest request = WdmSystemFixtures.createRequest();
        WdmSystem entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSystemName()).isEqualTo(request.getSystemName());
        assertThat(entity.getChannelCapacity()).isEqualTo(request.getChannelCapacity());
        assertThat(entity.getBand()).isEqualTo(request.getBand());
        assertThat(entity.getSpanCount()).isEqualTo(request.getSpanCount());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        WdmSystem entity = WdmSystemFixtures.entity();
        WdmSystemResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSystemName()).isEqualTo(entity.getSystemName());
        assertThat(response.getChannelCapacity()).isEqualTo(entity.getChannelCapacity());
        assertThat(response.getBand()).isEqualTo(entity.getBand());
        assertThat(response.getSpanCount()).isEqualTo(entity.getSpanCount());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        WdmSystem entity = WdmSystemFixtures.entity();
        WdmSystemSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(WdmSystemFixtures.entity())).hasSameSizeAs(WdmSystemMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        WdmSystem entity = WdmSystemFixtures.entity();
        WdmSystemUpdateRequest request = new WdmSystemUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSystemName("systemName-3");
        request.setChannelCapacity(13);
        request.setBand(WdmSystemBand.C_L);
        request.setSpanCount(13);
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(WdmSystemLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSystemName()).isEqualTo("systemName-3");
        assertThat(entity.getChannelCapacity()).isEqualTo(13);
        assertThat(entity.getBand()).isEqualTo(WdmSystemBand.C_L);
        assertThat(entity.getSpanCount()).isEqualTo(13);
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(WdmSystemLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        WdmSystem entity = WdmSystemFixtures.entity();
        WdmSystem before = WdmSystemFixtures.entity();
        mapper.applyUpdate(new WdmSystemUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
