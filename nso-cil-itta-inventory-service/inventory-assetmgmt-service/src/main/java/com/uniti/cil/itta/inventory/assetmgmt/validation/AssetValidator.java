package com.uniti.cil.itta.inventory.assetmgmt.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.assetmgmt.model.Asset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for Asset that go beyond bean validation. */
@Component
public class AssetValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(Asset item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("Asset failed validation", errors);
        }
    }

    public List<String> collect(Asset item) {
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
        if (item.getAssetTag() != null) {
            if (item.getAssetTag().length() > 255) {
                errors.add("assetTag must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getAssetTag()).find()) {
                errors.add("assetTag must not contain control characters");
            }
        }
        if (item.getAssetClass() != null) {
            if (item.getAssetClass().length() > 255) {
                errors.add("assetClass must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getAssetClass()).find()) {
                errors.add("assetClass must not contain control characters");
            }
        }
        if (item.getAcquiredOn() != null && item.getAcquiredOn().isBefore(MIN_DATE)) {
            errors.add("acquiredOn must not be before 1900-01-01");
        }
        if (item.getPurchasePrice() != null && item.getPurchasePrice().signum() < 0) {
            errors.add("purchasePrice must not be negative");
        }
        if (item.getCurrentValue() != null && item.getCurrentValue().signum() < 0) {
            errors.add("currentValue must not be negative");
        }
        if (item.getCustodian() != null) {
            if (item.getCustodian().length() > 255) {
                errors.add("custodian must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getCustodian()).find()) {
                errors.add("custodian must not contain control characters");
            }
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
