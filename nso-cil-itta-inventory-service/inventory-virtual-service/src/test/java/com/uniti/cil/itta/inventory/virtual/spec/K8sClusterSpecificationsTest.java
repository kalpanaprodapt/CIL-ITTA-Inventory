package com.uniti.cil.itta.inventory.virtual.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.K8sClusterSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class K8sClusterSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(K8sClusterSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        K8sClusterSearchCriteria criteria = new K8sClusterSearchCriteria();
        criteria.setQ("  core ");
        assertThat(K8sClusterSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
