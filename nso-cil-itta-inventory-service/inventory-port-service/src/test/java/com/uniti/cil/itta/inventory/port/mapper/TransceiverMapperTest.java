package com.uniti.cil.itta.inventory.port.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import com.uniti.cil.itta.inventory.port.support.TransceiverFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransceiverMapperTest {

    private final TransceiverMapper mapper = new TransceiverMapper();

    @Test
    void toEntityCopiesEveryField() {
        TransceiverCreateRequest request = TransceiverFixtures.createRequest();
        Transceiver entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPortId()).isEqualTo(request.getPortId());
        assertThat(entity.getFormFactor()).isEqualTo(request.getFormFactor());
        assertThat(entity.getWavelengthNm()).isEqualTo(request.getWavelengthNm());
        assertThat(entity.getReachKm()).isEqualTo(request.getReachKm());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getVendorPart()).isEqualTo(request.getVendorPart());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        Transceiver entity = TransceiverFixtures.entity();
        TransceiverResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPortId()).isEqualTo(entity.getPortId());
        assertThat(response.getFormFactor()).isEqualTo(entity.getFormFactor());
        assertThat(response.getWavelengthNm()).isEqualTo(entity.getWavelengthNm());
        assertThat(response.getReachKm()).isEqualTo(entity.getReachKm());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getVendorPart()).isEqualTo(entity.getVendorPart());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        Transceiver entity = TransceiverFixtures.entity();
        TransceiverSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TransceiverFixtures.entity())).hasSameSizeAs(TransceiverMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Transceiver entity = TransceiverFixtures.entity();
        TransceiverUpdateRequest request = new TransceiverUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setFormFactor(TransceiverFormFactor.SFP28);
        request.setWavelengthNm(13);
        request.setReachKm(new BigDecimal("15.50"));
        request.setSerialNumber("serialNumber-3");
        request.setVendorPart("vendorPart-3");
        request.setLabels("labels-3");
        request.setLifecycleStage(TransceiverLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPortId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getFormFactor()).isEqualTo(TransceiverFormFactor.SFP28);
        assertThat(entity.getWavelengthNm()).isEqualTo(13);
        assertThat(entity.getReachKm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getVendorPart()).isEqualTo("vendorPart-3");
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(TransceiverLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Transceiver entity = TransceiverFixtures.entity();
        Transceiver before = TransceiverFixtures.entity();
        mapper.applyUpdate(new TransceiverUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
