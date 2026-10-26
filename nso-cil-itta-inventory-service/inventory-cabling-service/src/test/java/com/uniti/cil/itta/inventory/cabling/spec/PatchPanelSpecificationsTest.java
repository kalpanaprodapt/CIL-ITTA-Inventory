package com.uniti.cil.itta.inventory.cabling.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.PatchPanelSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchPanelSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PatchPanelSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PatchPanelSearchCriteria criteria = new PatchPanelSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PatchPanelSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
