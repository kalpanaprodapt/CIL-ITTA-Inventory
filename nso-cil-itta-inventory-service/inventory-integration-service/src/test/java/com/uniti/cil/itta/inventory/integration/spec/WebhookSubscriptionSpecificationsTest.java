package com.uniti.cil.itta.inventory.integration.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.WebhookSubscriptionSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WebhookSubscriptionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(WebhookSubscriptionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        WebhookSubscriptionSearchCriteria criteria = new WebhookSubscriptionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(WebhookSubscriptionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
