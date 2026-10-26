package com.uniti.cil.itta.inventory.compliance.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.compliance.model.AuditRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for AuditRecord that go beyond bean validation. */
@Component
public class AuditRecordValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(AuditRecord item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("AuditRecord failed validation", errors);
        }
    }

    public List<String> collect(AuditRecord item) {
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
        if (item.getActor() != null) {
            if (item.getActor().length() > 255) {
                errors.add("actor must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getActor()).find()) {
                errors.add("actor must not contain control characters");
            }
        }
        if (item.getAction() != null) {
            if (item.getAction().length() > 255) {
                errors.add("action must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getAction()).find()) {
                errors.add("action must not contain control characters");
            }
        }
        if (item.getEntityRef() != null) {
            if (item.getEntityRef().length() > 255) {
                errors.add("entityRef must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getEntityRef()).find()) {
                errors.add("entityRef must not contain control characters");
            }
        }
        if (item.getBeforeState() != null) {
            if (item.getBeforeState().length() > 4000) {
                errors.add("beforeState must be at most 4000 characters");
            }
            if (CONTROL.matcher(item.getBeforeState()).find()) {
                errors.add("beforeState must not contain control characters");
            }
        }
        if (item.getAfterState() != null) {
            if (item.getAfterState().length() > 4000) {
                errors.add("afterState must be at most 4000 characters");
            }
            if (CONTROL.matcher(item.getAfterState()).find()) {
                errors.add("afterState must not contain control characters");
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
