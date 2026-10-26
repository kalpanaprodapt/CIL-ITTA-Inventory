package com.uniti.cil.itta.inventory.virtual.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.virtual.model.Vnf;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for Vnf that go beyond bean validation. */
@Component
public class VnfValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(Vnf item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("Vnf failed validation", errors);
        }
    }

    public List<String> collect(Vnf item) {
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
        if (item.getVnfName() != null) {
            if (item.getVnfName().length() > 255) {
                errors.add("vnfName must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getVnfName()).find()) {
                errors.add("vnfName must not contain control characters");
            }
        }
        if (item.getVnfType() != null) {
            if (item.getVnfType().length() > 255) {
                errors.add("vnfType must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getVnfType()).find()) {
                errors.add("vnfType must not contain control characters");
            }
        }
        if (item.getFlavor() != null) {
            if (item.getFlavor().length() > 255) {
                errors.add("flavor must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getFlavor()).find()) {
                errors.add("flavor must not contain control characters");
            }
        }
        if (item.getVcpu() != null && item.getVcpu() < 0) {
            errors.add("vcpu must not be negative");
        }
        if (item.getMemoryGb() != null && item.getMemoryGb() < 0) {
            errors.add("memoryGb must not be negative");
        }
        if (item.getInstanceCount() != null && item.getInstanceCount() < 0) {
            errors.add("instanceCount must not be negative");
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
