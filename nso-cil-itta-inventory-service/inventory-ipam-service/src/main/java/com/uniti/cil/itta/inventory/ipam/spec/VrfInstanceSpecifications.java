package com.uniti.cil.itta.inventory.ipam.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.VrfInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing VrfInstance search. */
public final class VrfInstanceSpecifications {

    private VrfInstanceSpecifications() {
    }

    public static Specification<VrfInstance> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<VrfInstance> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<VrfInstance> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<VrfInstance> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> vrfNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vrfName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> routeDistinguisherContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("routeDistinguisher")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> importTargetsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("importTargets")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> exportTargetsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("exportTargets")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> deviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("deviceId"), value);
    }

    public static Specification<VrfInstance> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> lifecycleStageIn(List<VrfInstanceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<VrfInstance> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VrfInstance> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<VrfInstance> fromCriteria(String tenantId, VrfInstanceSearchCriteria criteria) {
        Specification<VrfInstance> spec = tenant(tenantId);
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
        if (hasText(criteria.getVrfName())) {
            spec = spec.and(vrfNameContains(criteria.getVrfName().trim()));
        }
        if (hasText(criteria.getRouteDistinguisher())) {
            spec = spec.and(routeDistinguisherContains(criteria.getRouteDistinguisher().trim()));
        }
        if (hasText(criteria.getImportTargets())) {
            spec = spec.and(importTargetsContains(criteria.getImportTargets().trim()));
        }
        if (hasText(criteria.getExportTargets())) {
            spec = spec.and(exportTargetsContains(criteria.getExportTargets().trim()));
        }
        if (criteria.getDeviceId() != null) {
            spec = spec.and(deviceIdEquals(criteria.getDeviceId()));
        }
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
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
        return spec;
    }
}
