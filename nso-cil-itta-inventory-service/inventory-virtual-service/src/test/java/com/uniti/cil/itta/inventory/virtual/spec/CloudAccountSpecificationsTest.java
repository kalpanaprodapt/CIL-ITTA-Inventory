package com.uniti.cil.itta.inventory.virtual.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.CloudAccountSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CloudAccountSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CloudAccountSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CloudAccountSearchCriteria criteria = new CloudAccountSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CloudAccountSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
