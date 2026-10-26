package com.uniti.cil.itta.inventory.virtual.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.CloudAccountFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CloudAccountValidatorTest {

    private final CloudAccountValidator validator = new CloudAccountValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(CloudAccountFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        CloudAccount entity = CloudAccountFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        CloudAccount entity = CloudAccountFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        CloudAccount entity = CloudAccountFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        CloudAccount entity = CloudAccountFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
