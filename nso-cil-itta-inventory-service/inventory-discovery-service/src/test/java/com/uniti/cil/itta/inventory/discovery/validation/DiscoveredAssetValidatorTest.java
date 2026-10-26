package com.uniti.cil.itta.inventory.discovery.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveredAssetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveredAssetValidatorTest {

    private final DiscoveredAssetValidator validator = new DiscoveredAssetValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(DiscoveredAssetFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
