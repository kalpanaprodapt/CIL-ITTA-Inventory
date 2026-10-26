package com.uniti.cil.itta.inventory.device.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.NetworkDeviceSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class NetworkDeviceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(NetworkDeviceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        NetworkDeviceSearchCriteria criteria = new NetworkDeviceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(NetworkDeviceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
