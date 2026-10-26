package com.uniti.cil.itta.inventory.alarm.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.TicketLinkSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TicketLinkSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TicketLinkSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TicketLinkSearchCriteria criteria = new TicketLinkSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TicketLinkSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
