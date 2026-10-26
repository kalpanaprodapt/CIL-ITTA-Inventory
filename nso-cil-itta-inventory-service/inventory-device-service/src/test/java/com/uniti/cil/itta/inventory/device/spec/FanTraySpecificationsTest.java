package com.uniti.cil.itta.inventory.device.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.FanTraySearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FanTraySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(FanTraySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        FanTraySearchCriteria criteria = new FanTraySearchCriteria();
        criteria.setQ("  core ");
        assertThat(FanTraySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
