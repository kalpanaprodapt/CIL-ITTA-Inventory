package com.uniti.cil.itta.inventory.facility.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.RackUnitSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing RackUnit search. */
public final class RackUnitSpecifications {

    private RackUnitSpecifications() {
    }

    public static Specification<RackUnit> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<RackUnit> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<RackUnit> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<RackUnit> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> rackIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("rackId"), value);
    }

    public static Specification<RackUnit> unitNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("unitNumber"), value);
    }

    public static Specification<RackUnit> unitNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("unitNumber"), value);
    }

    public static Specification<RackUnit> occupiedEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("occupied"), value);
    }

    public static Specification<RackUnit> mountedDeviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("mountedDeviceId"), value);
    }

    public static Specification<RackUnit> faceIn(List<RackUnitFace> values) {
        return (root, query, cb) -> root.get("face").in(values);
    }

    public static Specification<RackUnit> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RackUnit> criticalityIn(List<RackUnitCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<RackUnit> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<RackUnit> fromCriteria(String tenantId, RackUnitSearchCriteria criteria) {
        Specification<RackUnit> spec = tenant(tenantId);
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
        if (criteria.getRackId() != null) {
            spec = spec.and(rackIdEquals(criteria.getRackId()));
        }
        if (criteria.getUnitNumberMin() != null) {
            spec = spec.and(unitNumberMin(criteria.getUnitNumberMin()));
        }
        if (criteria.getUnitNumberMax() != null) {
            spec = spec.and(unitNumberMax(criteria.getUnitNumberMax()));
        }
        if (criteria.getOccupied() != null) {
            spec = spec.and(occupiedEquals(criteria.getOccupied()));
        }
        if (criteria.getMountedDeviceId() != null) {
            spec = spec.and(mountedDeviceIdEquals(criteria.getMountedDeviceId()));
        }
        if (criteria.getFace() != null && !criteria.getFace().isEmpty()) {
            spec = spec.and(faceIn(criteria.getFace()));
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
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
