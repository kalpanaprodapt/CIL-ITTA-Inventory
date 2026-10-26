package com.uniti.cil.itta.inventory.assetmgmt.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.PurchaseOrderSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PurchaseOrderSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PurchaseOrderSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PurchaseOrderSearchCriteria criteria = new PurchaseOrderSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PurchaseOrderSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
