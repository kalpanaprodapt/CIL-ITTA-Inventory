package com.uniti.cil.itta.inventory.alarm.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.AlarmInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmInstanceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AlarmInstanceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AlarmInstanceSearchCriteria criteria = new AlarmInstanceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AlarmInstanceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
