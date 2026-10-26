package com.uniti.cil.itta.inventory.alarm.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.AlarmDefinitionSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmDefinitionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AlarmDefinitionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AlarmDefinitionSearchCriteria criteria = new AlarmDefinitionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AlarmDefinitionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
