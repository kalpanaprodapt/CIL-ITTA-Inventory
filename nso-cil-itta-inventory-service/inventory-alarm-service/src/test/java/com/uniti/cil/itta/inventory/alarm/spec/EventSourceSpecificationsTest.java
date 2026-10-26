package com.uniti.cil.itta.inventory.alarm.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.EventSourceSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventSourceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(EventSourceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        EventSourceSearchCriteria criteria = new EventSourceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(EventSourceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
