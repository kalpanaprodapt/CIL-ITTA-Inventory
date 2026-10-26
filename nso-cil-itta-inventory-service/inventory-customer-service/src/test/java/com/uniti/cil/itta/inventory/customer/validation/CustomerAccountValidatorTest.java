package com.uniti.cil.itta.inventory.customer.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.CustomerAccountFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerAccountValidatorTest {

    private final CustomerAccountValidator validator = new CustomerAccountValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(CustomerAccountFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
