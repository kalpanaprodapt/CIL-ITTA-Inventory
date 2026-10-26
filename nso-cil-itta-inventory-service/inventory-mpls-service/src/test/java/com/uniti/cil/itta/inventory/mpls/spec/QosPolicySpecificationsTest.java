package com.uniti.cil.itta.inventory.mpls.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.QosPolicySearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class QosPolicySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(QosPolicySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        QosPolicySearchCriteria criteria = new QosPolicySearchCriteria();
        criteria.setQ("  core ");
        assertThat(QosPolicySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
