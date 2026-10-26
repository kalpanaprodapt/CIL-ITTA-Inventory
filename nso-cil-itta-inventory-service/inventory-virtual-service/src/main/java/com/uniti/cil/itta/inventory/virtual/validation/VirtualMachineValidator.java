package com.uniti.cil.itta.inventory.virtual.validation;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.virtual.model.VirtualMachine;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/** Business rules for VirtualMachine that go beyond bean validation. */
@Component
public class VirtualMachineValidator {

    private static final Pattern CONTROL = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final UUID NIL = new UUID(0L, 0L);

    public void validate(VirtualMachine item) {
        List<String> errors = collect(item);
        if (!errors.isEmpty()) {
            throw new ValidationFailedException("VirtualMachine failed validation", errors);
        }
    }

    public List<String> collect(VirtualMachine item) {
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
        if (item.getHypervisorId() != null && NIL.equals(item.getHypervisorId())) {
            errors.add("hypervisorId must not be the nil UUID");
        }
        if (item.getVmName() != null) {
            if (item.getVmName().length() > 255) {
                errors.add("vmName must be at most 255 characters");
            }
            if (CONTROL.matcher(item.getVmName()).find()) {
                errors.add("vmName must not contain control characters");
            }
        }
        if (item.getVcpu() != null && item.getVcpu() < 0) {
            errors.add("vcpu must not be negative");
        }
        if (item.getMemoryGb() != null && item.getMemoryGb() < 0) {
            errors.add("memoryGb must not be negative");
        }
        if (item.getDiskGb() != null && item.getDiskGb() < 0) {
            errors.add("diskGb must not be negative");
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
