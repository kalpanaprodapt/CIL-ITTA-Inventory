package com.uniti.cil.itta.inventory.compliance.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.compliance.model.PolicyDocument;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for PolicyDocument that go beyond bean validation. */
@Component
public class PolicyDocumentValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(PolicyDocument item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("PolicyDocument failed validation", errors);
        }
    }

    public List<String> collect(PolicyDocument item) {
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
        if (item.getTitle() != null) {
            if (item.getTitle().length() > 255) {
                errors.add("title must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getTitle()).find()) {
                errors.add("title must not contain control characters");
            }
        }
        if (item.getDocVersion() != null) {
            if (item.getDocVersion().length() > 255) {
                errors.add("docVersion must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getDocVersion()).find()) {
                errors.add("docVersion must not contain control characters");
            }
        }
        if (item.getEffectiveDate() != null && item.getEffectiveDate().isBefore(MIN_DATE)) {
            errors.add("effectiveDate must not be before 1900-01-01");
        }
        if (item.getOwner() != null) {
            if (item.getOwner().length() > 255) {
                errors.add("owner must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getOwner()).find()) {
                errors.add("owner must not contain control characters");
            }
        }
        if (item.getDocumentUrl() != null) {
            if (item.getDocumentUrl().length() > 255) {
                errors.add("documentUrl must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getDocumentUrl()).find()) {
                errors.add("documentUrl must not contain control characters");
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
        if (item.getRegion() != null) {
            if (item.getRegion().length() > 255) {
                errors.add("region must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getRegion()).find()) {
                errors.add("region must not contain control characters");
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
        return errors;
    }
}
