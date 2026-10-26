package com.uniti.cil.itta.inventory.topology.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.LinkFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LinkValidatorTest {

    private final LinkValidator validator = new LinkValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(LinkFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Link entity = LinkFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Link entity = LinkFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Link entity = LinkFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Link entity = LinkFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
