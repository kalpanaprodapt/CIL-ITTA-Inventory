package com.uniti.cil.itta.inventory.switching.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.BgpSessionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BgpSessionValidatorTest {

    private final BgpSessionValidator validator = new BgpSessionValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(BgpSessionFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        BgpSession entity = BgpSessionFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        BgpSession entity = BgpSessionFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        BgpSession entity = BgpSessionFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        BgpSession entity = BgpSessionFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
