package com.uniti.cil.itta.inventory.ipam.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.IpSubnetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpSubnetValidatorTest {

    private final IpSubnetValidator validator = new IpSubnetValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(IpSubnetFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        IpSubnet entity = IpSubnetFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        IpSubnet entity = IpSubnetFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        IpSubnet entity = IpSubnetFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        IpSubnet entity = IpSubnetFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
