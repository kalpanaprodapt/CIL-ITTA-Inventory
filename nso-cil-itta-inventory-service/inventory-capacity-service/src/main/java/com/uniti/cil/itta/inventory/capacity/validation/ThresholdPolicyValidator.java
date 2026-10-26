package com.uniti.cil.itta.inventory.capacity.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.capacity.model.ThresholdPolicy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for ThresholdPolicy that go beyond bean validation. */
@Component
public class ThresholdPolicyValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(ThresholdPolicy item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("ThresholdPolicy failed validation", errors);
        }
    }

    public List<String> collect(ThresholdPolicy item) {
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
        if (item.getPolicyName() != null) {
            if (item.getPolicyName().length() > 255) {
                errors.add("policyName must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getPolicyName()).find()) {
                errors.add("policyName must not contain control characters");
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
        if (item.getWarnPct() != null && item.getWarnPct().signum() < 0) {
            errors.add("warnPct must not be negative");
        }
        if (item.getCritPct() != null && item.getCritPct().signum() < 0) {
            errors.add("critPct must not be negative");
        }
        if (item.getEvaluationWindowMinutes() != null && item.getEvaluationWindowMinutes() < 0) {
            errors.add("evaluationWindowMinutes must not be negative");
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
        return errors;
    }
}
