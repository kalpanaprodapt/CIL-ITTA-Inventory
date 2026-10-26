package com.uniti.cil.itta.inventory.geography.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.geography.model.Country;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for Country that go beyond bean validation. */
@Component
public class CountryValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(Country item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("Country failed validation", errors);
        }
    }

    public List<String> collect(Country item) {
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
        if (item.getIsoCode() != null) {
            if (item.getIsoCode().length() > 255) {
                errors.add("isoCode must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getIsoCode()).find()) {
                errors.add("isoCode must not contain control characters");
            }
        }
        if (item.getIsoNumeric() != null && item.getIsoNumeric() < 0) {
            errors.add("isoNumeric must not be negative");
        }
        if (item.getDialCode() != null) {
            if (item.getDialCode().length() > 255) {
                errors.add("dialCode must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getDialCode()).find()) {
                errors.add("dialCode must not contain control characters");
            }
        }
        if (item.getCurrency() != null) {
            if (item.getCurrency().length() > 255) {
                errors.add("currency must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getCurrency()).find()) {
                errors.add("currency must not contain control characters");
            }
        }
        if (item.getRegulatoryBody() != null) {
            if (item.getRegulatoryBody().length() > 255) {
                errors.add("regulatoryBody must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getRegulatoryBody()).find()) {
                errors.add("regulatoryBody must not contain control characters");
            }
        }
        if (item.getTimezone() != null) {
            if (item.getTimezone().length() > 255) {
                errors.add("timezone must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getTimezone()).find()) {
                errors.add("timezone must not contain control characters");
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
