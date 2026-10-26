package com.uniti.cil.itta.inventory.capacity.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.capacity.model.UtilizationSample;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for UtilizationSample that go beyond bean validation. */
@Component
public class UtilizationSampleValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(UtilizationSample item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("UtilizationSample failed validation", errors);
        }
    }

    public List<String> collect(UtilizationSample item) {
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
        if (item.getTargetRef() != null) {
            if (item.getTargetRef().length() > 255) {
                errors.add("targetRef must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getTargetRef()).find()) {
                errors.add("targetRef must not contain control characters");
            }
        }
        if (item.getMetric() != null) {
            if (item.getMetric().length() > 255) {
                errors.add("metric must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getMetric()).find()) {
                errors.add("metric must not contain control characters");
            }
        }
        if (item.getValuePct() != null && item.getValuePct().signum() < 0) {
            errors.add("valuePct must not be negative");
        }
        if (item.getSourceSystem() != null) {
            if (item.getSourceSystem().length() > 255) {
                errors.add("sourceSystem must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getSourceSystem()).find()) {
                errors.add("sourceSystem must not contain control characters");
            }
        }
        if (item.getRegion() != null) {
            if (item.getRegion().length() > 255) {
                errors.add("region must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getRegion()).find()) {
                errors.add("region must not contain control characters");
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
        return errors;
    }
}
