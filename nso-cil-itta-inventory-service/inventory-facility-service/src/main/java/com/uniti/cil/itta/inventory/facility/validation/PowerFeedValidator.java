package com.uniti.cil.itta.inventory.facility.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.facility.model.PowerFeed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for PowerFeed that go beyond bean validation. */
@Component
public class PowerFeedValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(PowerFeed item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("PowerFeed failed validation", errors);
        }
    }

    public List<String> collect(PowerFeed item) {
        List<String> errors = new ArrayList<>();
        if (item.getCode() != null) {
            if (item.getCode().length() > 255) {
                errors.add("code must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getCode()).find()) {
                errors.add("code must not contain control characters");
            }
        }
        if (item.getName() != null) {
            if (item.getName().length() > 255) {
                errors.add("name must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getName()).find()) {
                errors.add("name must not contain control characters");
            }
        }
        if (item.getDescription() != null) {
            if (item.getDescription().length() > 4000) {
                errors.add("description must be at most 4000 characters");
            }
            if (CONTROL.matcher(item.getDescription()).find()) {
                errors.add("description must not contain control characters");
            }
        }
        if (item.getExternalRef() != null) {
            if (item.getExternalRef().length() > 255) {
                errors.add("externalRef must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getExternalRef()).find()) {
                errors.add("externalRef must not contain control characters");
            }
        }
        if (item.getRoomId() != null && NIL.equals(item.getRoomId())) {
            errors.add("roomId must not be the nil UUID");
        }
        if (item.getVoltage() != null && item.getVoltage().signum() < 0) {
            errors.add("voltage must not be negative");
        }
        if (item.getAmperage() != null && item.getAmperage().signum() < 0) {
            errors.add("amperage must not be negative");
        }
        if (item.getPhase() != null) {
            if (item.getPhase().length() > 255) {
                errors.add("phase must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getPhase()).find()) {
                errors.add("phase must not contain control characters");
            }
        }
        if (item.getBreakerLabel() != null) {
            if (item.getBreakerLabel().length() > 255) {
                errors.add("breakerLabel must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getBreakerLabel()).find()) {
                errors.add("breakerLabel must not contain control characters");
            }
        }
        if (item.getRedundancyGroup() != null) {
            if (item.getRedundancyGroup().length() > 255) {
                errors.add("redundancyGroup must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getRedundancyGroup()).find()) {
                errors.add("redundancyGroup must not contain control characters");
            }
        }
        if (item.getOwner() != null) {
            if (item.getOwner().length() > 255) {
                errors.add("owner must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getOwner()).find()) {
                errors.add("owner must not contain control characters");
            }
        }
        if (item.getCostCenter() != null) {
            if (item.getCostCenter().length() > 255) {
                errors.add("costCenter must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getCostCenter()).find()) {
                errors.add("costCenter must not contain control characters");
            }
        }
        if (item.getNotes() != null) {
            if (item.getNotes().length() > 4000) {
                errors.add("notes must be at most 4000 characters");
            }
            if (CONTROL.matcher(item.getNotes()).find()) {
                errors.add("notes must not contain control characters");
            }
        }
        if (item.getEffectiveFrom() != null && item.getEffectiveFrom().isBefore(MIN_DATE)) {
            errors.add("effectiveFrom must not be before 1900-01-01");
        }
        return errors;
    }
}
