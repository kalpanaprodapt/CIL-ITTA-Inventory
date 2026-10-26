package com.uniti.cil.itta.inventory.cabling.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.FiberStrandSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing FiberStrand search. */
public final class FiberStrandSpecifications {

    private FiberStrandSpecifications() {
    }

    public static Specification<FiberStrand> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<FiberStrand> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<FiberStrand> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<FiberStrand> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> cableIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("cableId"), value);
    }

    public static Specification<FiberStrand> strandNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("strandNumber"), value);
    }

    public static Specification<FiberStrand> strandNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("strandNumber"), value);
    }

    public static Specification<FiberStrand> bufferTubeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("bufferTube")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> tubeColorContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("tubeColor")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> attenuationDbMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("attenuationDb"), value);
    }

    public static Specification<FiberStrand> attenuationDbMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("attenuationDb"), value);
    }

    public static Specification<FiberStrand> inUseEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("inUse"), value);
    }

    public static Specification<FiberStrand> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FiberStrand> criticalityIn(List<FiberStrandCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<FiberStrand> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<FiberStrand> fromCriteria(String tenantId, FiberStrandSearchCriteria criteria) {
        Specification<FiberStrand> spec = tenant(tenantId);
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
        if (criteria.getCableId() != null) {
            spec = spec.and(cableIdEquals(criteria.getCableId()));
        }
        if (criteria.getStrandNumberMin() != null) {
            spec = spec.and(strandNumberMin(criteria.getStrandNumberMin()));
        }
        if (criteria.getStrandNumberMax() != null) {
            spec = spec.and(strandNumberMax(criteria.getStrandNumberMax()));
        }
        if (hasText(criteria.getBufferTube())) {
            spec = spec.and(bufferTubeContains(criteria.getBufferTube().trim()));
        }
        if (hasText(criteria.getTubeColor())) {
            spec = spec.and(tubeColorContains(criteria.getTubeColor().trim()));
        }
        if (criteria.getAttenuationDbMin() != null) {
            spec = spec.and(attenuationDbMin(criteria.getAttenuationDbMin()));
        }
        if (criteria.getAttenuationDbMax() != null) {
            spec = spec.and(attenuationDbMax(criteria.getAttenuationDbMax()));
        }
        if (criteria.getInUse() != null) {
            spec = spec.and(inUseEquals(criteria.getInUse()));
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
