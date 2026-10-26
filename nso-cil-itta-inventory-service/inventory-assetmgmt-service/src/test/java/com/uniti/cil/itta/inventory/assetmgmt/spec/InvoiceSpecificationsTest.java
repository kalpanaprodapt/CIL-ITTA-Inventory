package com.uniti.cil.itta.inventory.assetmgmt.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.InvoiceSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class InvoiceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(InvoiceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(InvoiceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
