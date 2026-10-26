package com.uniti.cil.itta.inventory.switching.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.BgpSessionSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BgpSessionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(BgpSessionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        BgpSessionSearchCriteria criteria = new BgpSessionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(BgpSessionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
