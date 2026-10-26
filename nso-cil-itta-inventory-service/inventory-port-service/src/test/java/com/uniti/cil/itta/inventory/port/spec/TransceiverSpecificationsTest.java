package com.uniti.cil.itta.inventory.port.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.TransceiverSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransceiverSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TransceiverSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TransceiverSearchCriteria criteria = new TransceiverSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TransceiverSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
