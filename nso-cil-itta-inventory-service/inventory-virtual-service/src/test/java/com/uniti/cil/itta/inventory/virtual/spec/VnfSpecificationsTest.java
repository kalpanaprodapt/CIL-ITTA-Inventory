package com.uniti.cil.itta.inventory.virtual.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.VnfSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VnfSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VnfSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VnfSearchCriteria criteria = new VnfSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VnfSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
