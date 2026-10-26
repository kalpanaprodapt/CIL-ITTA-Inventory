package com.uniti.cil.itta.inventory.switching.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.switching.model.Vlan;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for Vlan that go beyond bean validation. */
@Component
public class VlanValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(Vlan item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("Vlan failed validation", errors);
        }
    }

    public List<String> collect(Vlan item) {
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
        if (item.getVlanId() != null && item.getVlanId() < 0) {
            errors.add("vlanId must not be negative");
        }
        if (item.getVlanName() != null) {
            if (item.getVlanName().length() > 255) {
                errors.add("vlanName must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getVlanName()).find()) {
                errors.add("vlanName must not contain control characters");
            }
        }
        if (item.getPoolId() != null && NIL.equals(item.getPoolId())) {
            errors.add("poolId must not be the nil UUID");
        }
        if (item.getStpMode() != null) {
            if (item.getStpMode().length() > 255) {
                errors.add("stpMode must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getStpMode()).find()) {
                errors.add("stpMode must not contain control characters");
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
        if (item.getEffectiveTo() != null && item.getEffectiveTo().isBefore(MIN_DATE)) {
            errors.add("effectiveTo must not be before 1900-01-01");
        }
        if (item.getLabels() != null) {
            if (item.getLabels().length() > 255) {
                errors.add("labels must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getLabels()).find()) {
                errors.add("labels must not contain control characters");
            }
        }
        if (item.getEffectiveFrom() != null && item.getEffectiveTo() != null
                && item.getEffectiveTo().compareTo(item.getEffectiveFrom()) < 0) {
            errors.add("effectiveTo must not be earlier than effectiveFrom");
        }
        return errors;
    }
}
