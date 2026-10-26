package com.uniti.cil.itta.inventory.virtual.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.HypervisorSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Hypervisor search. */
public final class HypervisorSpecifications {

    private HypervisorSpecifications() {
    }

    public static Specification<Hypervisor> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Hypervisor> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Hypervisor> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Hypervisor> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> hostDeviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("hostDeviceId"), value);
    }

    public static Specification<Hypervisor> hypervisorTypeIn(List<HypervisorHypervisorType> values) {
        return (root, query, cb) -> root.get("hypervisorType").in(values);
    }

    public static Specification<Hypervisor> hvVersionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("hvVersion")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> vcpuCapacityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("vcpuCapacity"), value);
    }

    public static Specification<Hypervisor> vcpuCapacityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("vcpuCapacity"), value);
    }

    public static Specification<Hypervisor> memoryGbCapacityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("memoryGbCapacity"), value);
    }

    public static Specification<Hypervisor> memoryGbCapacityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("memoryGbCapacity"), value);
    }

    public static Specification<Hypervisor> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Hypervisor> criticalityIn(List<HypervisorCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Hypervisor> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Hypervisor> fromCriteria(String tenantId, HypervisorSearchCriteria criteria) {
        Specification<Hypervisor> spec = tenant(tenantId);
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
        if (criteria.getHostDeviceId() != null) {
            spec = spec.and(hostDeviceIdEquals(criteria.getHostDeviceId()));
        }
        if (criteria.getHypervisorType() != null && !criteria.getHypervisorType().isEmpty()) {
            spec = spec.and(hypervisorTypeIn(criteria.getHypervisorType()));
        }
        if (hasText(criteria.getHvVersion())) {
            spec = spec.and(hvVersionContains(criteria.getHvVersion().trim()));
        }
        if (criteria.getVcpuCapacityMin() != null) {
            spec = spec.and(vcpuCapacityMin(criteria.getVcpuCapacityMin()));
        }
        if (criteria.getVcpuCapacityMax() != null) {
            spec = spec.and(vcpuCapacityMax(criteria.getVcpuCapacityMax()));
        }
        if (criteria.getMemoryGbCapacityMin() != null) {
            spec = spec.and(memoryGbCapacityMin(criteria.getMemoryGbCapacityMin()));
        }
        if (criteria.getMemoryGbCapacityMax() != null) {
            spec = spec.and(memoryGbCapacityMax(criteria.getMemoryGbCapacityMax()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
