package com.uniti.cil.itta.inventory.alarm.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.alarm.model.AlarmInstance;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for AlarmInstance that go beyond bean validation. */
@Component
public class AlarmInstanceValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(AlarmInstance item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("AlarmInstance failed validation", errors);
        }
    }

    public List<String> collect(AlarmInstance item) {
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
        if (item.getDefinitionId() != null && NIL.equals(item.getDefinitionId())) {
            errors.add("definitionId must not be the nil UUID");
        }
        if (item.getSourceRef() != null) {
            if (item.getSourceRef().length() > 255) {
                errors.add("sourceRef must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getSourceRef()).find()) {
                errors.add("sourceRef must not contain control characters");
            }
        }
        if (item.getAckedBy() != null) {
            if (item.getAckedBy().length() > 255) {
                errors.add("ackedBy must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getAckedBy()).find()) {
                errors.add("ackedBy must not contain control characters");
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
        if (item.getSourceSystem() != null) {
            if (item.getSourceSystem().length() > 255) {
                errors.add("sourceSystem must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getSourceSystem()).find()) {
                errors.add("sourceSystem must not contain control characters");
            }
        }
        if (item.getEffectiveFrom() != null && item.getEffectiveTo() != null
                && item.getEffectiveTo().compareTo(item.getEffectiveFrom()) < 0) {
            errors.add("effectiveTo must not be earlier than effectiveFrom");
        }
        if (item.getRaisedAt() != null && item.getClearedAt() != null
                && item.getClearedAt().compareTo(item.getRaisedAt()) < 0) {
            errors.add("clearedAt must not be earlier than raisedAt");
        }
        return errors;
    }
}
