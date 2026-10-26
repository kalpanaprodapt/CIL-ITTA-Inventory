package com.uniti.cil.itta.inventory.mpls.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.PseudoWireFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PseudoWireMapperTest {

    private final PseudoWireMapper mapper = new PseudoWireMapper();

    @Test
    void toEntityCopiesEveryField() {
        PseudoWireCreateRequest request = PseudoWireFixtures.createRequest();
        PseudoWire entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVcId()).isEqualTo(request.getVcId());
        assertThat(entity.getAEndId()).isEqualTo(request.getAEndId());
        assertThat(entity.getZEndId()).isEqualTo(request.getZEndId());
        assertThat(entity.getEncapsulation()).isEqualTo(request.getEncapsulation());
        assertThat(entity.getControlWord()).isEqualTo(request.getControlWord());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        PseudoWire entity = PseudoWireFixtures.entity();
        PseudoWireResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVcId()).isEqualTo(entity.getVcId());
        assertThat(response.getAEndId()).isEqualTo(entity.getAEndId());
        assertThat(response.getZEndId()).isEqualTo(entity.getZEndId());
        assertThat(response.getEncapsulation()).isEqualTo(entity.getEncapsulation());
        assertThat(response.getControlWord()).isEqualTo(entity.getControlWord());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        PseudoWire entity = PseudoWireFixtures.entity();
        PseudoWireSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PseudoWireFixtures.entity())).hasSameSizeAs(PseudoWireMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PseudoWire entity = PseudoWireFixtures.entity();
        PseudoWireUpdateRequest request = new PseudoWireUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVcId(1003L);
        request.setAEndId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZEndId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEncapsulation("encapsulation-3");
        request.setControlWord(Boolean.TRUE);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVcId()).isEqualTo(1003L);
        assertThat(entity.getAEndId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZEndId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEncapsulation()).isEqualTo("encapsulation-3");
        assertThat(entity.getControlWord()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PseudoWire entity = PseudoWireFixtures.entity();
        PseudoWire before = PseudoWireFixtures.entity();
        mapper.applyUpdate(new PseudoWireUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
