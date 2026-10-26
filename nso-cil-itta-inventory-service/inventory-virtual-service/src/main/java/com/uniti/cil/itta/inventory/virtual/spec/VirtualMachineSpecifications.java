package com.uniti.cil.itta.inventory.virtual.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.VirtualMachineSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing VirtualMachine search. */
public final class VirtualMachineSpecifications {

    private VirtualMachineSpecifications() {
    }

    public static Specification<VirtualMachine> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<VirtualMachine> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<VirtualMachine> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<VirtualMachine> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VirtualMachine> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VirtualMachine> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VirtualMachine> hypervisorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("hypervisorId"), value);
    }

    public static Specification<VirtualMachine> vmNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vmName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VirtualMachine> vcpuMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("vcpu"), value);
    }

    public static Specification<VirtualMachine> vcpuMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("vcpu"), value);
    }

    public static Specification<VirtualMachine> memoryGbMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("memoryGb"), value);
    }

    public static Specification<VirtualMachine> memoryGbMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("memoryGb"), value);
    }

    public static Specification<VirtualMachine> diskGbMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("diskGb"), value);
    }

    public static Specification<VirtualMachine> diskGbMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("diskGb"), value);
    }

    public static Specification<VirtualMachine> powerStateIn(List<VirtualMachinePowerState> values) {
        return (root, query, cb) -> root.get("powerState").in(values);
    }

    public static Specification<VirtualMachine> criticalityIn(List<VirtualMachineCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<VirtualMachine> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VirtualMachine> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<VirtualMachine> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<VirtualMachine> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<VirtualMachine> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<VirtualMachine> fromCriteria(String tenantId, VirtualMachineSearchCriteria criteria) {
        Specification<VirtualMachine> spec = tenant(tenantId);
        if (criteria == null) {
            return spec;
        }
        if (hasText(criteria.getQ())) {
            spec = spec.and(freeText(criteria.getQ().trim()));
        }
        if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
            spec = spec.and(statusIn(criteria.getStatus()));
        }
        if (hasText(criteria.getCode())) {
            spec = spec.and(codeContains(criteria.getCode().trim()));
        }
        if (hasText(criteria.getName())) {
            spec = spec.and(nameContains(criteria.getName().trim()));
        }
        if (hasText(criteria.getExternalRef())) {
            spec = spec.and(externalRefContains(criteria.getExternalRef().trim()));
        }
        if (criteria.getHypervisorId() != null) {
            spec = spec.and(hypervisorIdEquals(criteria.getHypervisorId()));
        }
        if (hasText(criteria.getVmName())) {
            spec = spec.and(vmNameContains(criteria.getVmName().trim()));
        }
        if (criteria.getVcpuMin() != null) {
            spec = spec.and(vcpuMin(criteria.getVcpuMin()));
        }
        if (criteria.getVcpuMax() != null) {
            spec = spec.and(vcpuMax(criteria.getVcpuMax()));
        }
        if (criteria.getMemoryGbMin() != null) {
            spec = spec.and(memoryGbMin(criteria.getMemoryGbMin()));
        }
        if (criteria.getMemoryGbMax() != null) {
            spec = spec.and(memoryGbMax(criteria.getMemoryGbMax()));
        }
        if (criteria.getDiskGbMin() != null) {
            spec = spec.and(diskGbMin(criteria.getDiskGbMin()));
        }
        if (criteria.getDiskGbMax() != null) {
            spec = spec.and(diskGbMax(criteria.getDiskGbMax()));
        }
        if (criteria.getPowerState() != null && !criteria.getPowerState().isEmpty()) {
            spec = spec.and(powerStateIn(criteria.getPowerState()));
        }
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
        }
        return spec;
    }
}
