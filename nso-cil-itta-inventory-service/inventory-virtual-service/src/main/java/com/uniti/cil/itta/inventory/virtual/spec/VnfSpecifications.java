package com.uniti.cil.itta.inventory.virtual.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.VnfSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Vnf search. */
public final class VnfSpecifications {

    private VnfSpecifications() {
    }

    public static Specification<Vnf> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Vnf> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Vnf> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Vnf> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> vnfNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vnfName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> vnfTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vnfType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> flavorContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("flavor")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> vcpuMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("vcpu"), value);
    }

    public static Specification<Vnf> vcpuMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("vcpu"), value);
    }

    public static Specification<Vnf> memoryGbMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("memoryGb"), value);
    }

    public static Specification<Vnf> memoryGbMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("memoryGb"), value);
    }

    public static Specification<Vnf> instanceCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("instanceCount"), value);
    }

    public static Specification<Vnf> instanceCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("instanceCount"), value);
    }

    public static Specification<Vnf> lifecycleStageIn(List<VnfLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Vnf> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vnf> criticalityIn(List<VnfCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Vnf> fromCriteria(String tenantId, VnfSearchCriteria criteria) {
        Specification<Vnf> spec = tenant(tenantId);
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
        if (hasText(criteria.getVnfName())) {
            spec = spec.and(vnfNameContains(criteria.getVnfName().trim()));
        }
        if (hasText(criteria.getVnfType())) {
            spec = spec.and(vnfTypeContains(criteria.getVnfType().trim()));
        }
        if (hasText(criteria.getFlavor())) {
            spec = spec.and(flavorContains(criteria.getFlavor().trim()));
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
        if (criteria.getInstanceCountMin() != null) {
            spec = spec.and(instanceCountMin(criteria.getInstanceCountMin()));
        }
        if (criteria.getInstanceCountMax() != null) {
            spec = spec.and(instanceCountMax(criteria.getInstanceCountMax()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
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
        return spec;
    }
}
