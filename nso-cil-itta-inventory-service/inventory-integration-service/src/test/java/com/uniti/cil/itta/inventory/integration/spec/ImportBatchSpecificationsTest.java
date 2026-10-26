package com.uniti.cil.itta.inventory.integration.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.ImportBatchSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ImportBatchSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ImportBatchSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ImportBatchSearchCriteria criteria = new ImportBatchSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ImportBatchSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
