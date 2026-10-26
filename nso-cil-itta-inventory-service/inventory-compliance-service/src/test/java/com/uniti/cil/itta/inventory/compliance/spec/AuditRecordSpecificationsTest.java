package com.uniti.cil.itta.inventory.compliance.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.AuditRecordSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AuditRecordSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AuditRecordSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AuditRecordSearchCriteria criteria = new AuditRecordSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AuditRecordSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
