package com.uniti.cil.itta.inventory.change.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.change.model.Technician;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for Technician that go beyond bean validation. */
@Component
public class TechnicianValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(Technician item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("Technician failed validation", errors);
        }
    }

    public List<String> collect(Technician item) {
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
        if (item.getEmployeeId() != null) {
            if (item.getEmployeeId().length() > 255) {
                errors.add("employeeId must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getEmployeeId()).find()) {
                errors.add("employeeId must not contain control characters");
            }
        }
        if (item.getFullName() != null) {
            if (item.getFullName().length() > 255) {
                errors.add("fullName must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getFullName()).find()) {
                errors.add("fullName must not contain control characters");
            }
        }
        if (item.getSkillSet() != null) {
            if (item.getSkillSet().length() > 255) {
                errors.add("skillSet must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getSkillSet()).find()) {
                errors.add("skillSet must not contain control characters");
            }
        }
        if (item.getHomeSiteId() != null && NIL.equals(item.getHomeSiteId())) {
            errors.add("homeSiteId must not be the nil UUID");
        }
        if (item.getCertificationLevel() != null && item.getCertificationLevel() < 0) {
            errors.add("certificationLevel must not be negative");
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
        if (item.getEffectiveTo() != null && item.getEffectiveTo().isBefore(MIN_DATE)) {
            errors.add("effectiveTo must not be before 1900-01-01");
        }
        if (item.getEffectiveFrom() != null && item.getEffectiveTo() != null
                && item.getEffectiveTo().compareTo(item.getEffectiveFrom()) < 0) {
            errors.add("effectiveTo must not be earlier than effectiveFrom");
        }
        return errors;
    }
}
