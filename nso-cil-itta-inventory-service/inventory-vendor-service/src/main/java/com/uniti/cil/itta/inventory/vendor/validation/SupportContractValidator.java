package com.uniti.cil.itta.inventory.vendor.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.vendor.model.SupportContract;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for SupportContract that go beyond bean validation. */
@Component
public class SupportContractValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(SupportContract item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("SupportContract failed validation", errors);
        }
    }

    public List<String> collect(SupportContract item) {
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
        if (item.getVendorId() != null && NIL.equals(item.getVendorId())) {
            errors.add("vendorId must not be the nil UUID");
        }
        if (item.getContractNumber() != null) {
            if (item.getContractNumber().length() > 255) {
                errors.add("contractNumber must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getContractNumber()).find()) {
                errors.add("contractNumber must not contain control characters");
            }
        }
        if (item.getStartDate() != null && item.getStartDate().isBefore(MIN_DATE)) {
            errors.add("startDate must not be before 1900-01-01");
        }
        if (item.getEndDate() != null && item.getEndDate().isBefore(MIN_DATE)) {
            errors.add("endDate must not be before 1900-01-01");
        }
        if (item.getAnnualCost() != null && item.getAnnualCost().signum() < 0) {
            errors.add("annualCost must not be negative");
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
        if (item.getStartDate() != null && item.getEndDate() != null
                && item.getEndDate().compareTo(item.getStartDate()) < 0) {
            errors.add("endDate must not be earlier than startDate");
        }
        return errors;
    }
}
