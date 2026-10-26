package com.uniti.cil.itta.inventory.circuit.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Circuit search. */
public final class CircuitSpecifications {

    private CircuitSpecifications() {
    }

    public static Specification<Circuit> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Circuit> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Circuit> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Circuit> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> circuitIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("circuitId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> circuitTypeIn(List<CircuitCircuitType> values) {
        return (root, query, cb) -> root.get("circuitType").in(values);
    }

    public static Specification<Circuit> bandwidthMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<Circuit> bandwidthMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<Circuit> providerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("providerId"), value);
    }

    public static Specification<Circuit> aSiteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aSiteId"), value);
    }

    public static Specification<Circuit> zSiteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zSiteId"), value);
    }

    public static Specification<Circuit> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> lifecycleStageIn(List<CircuitLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Circuit> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Circuit> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Circuit> fromCriteria(String tenantId, CircuitSearchCriteria criteria) {
        Specification<Circuit> spec = tenant(tenantId);
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
        if (hasText(criteria.getCircuitId())) {
            spec = spec.and(circuitIdContains(criteria.getCircuitId().trim()));
        }
        if (criteria.getCircuitType() != null && !criteria.getCircuitType().isEmpty()) {
            spec = spec.and(circuitTypeIn(criteria.getCircuitType()));
        }
        if (criteria.getBandwidthMbpsMin() != null) {
            spec = spec.and(bandwidthMbpsMin(criteria.getBandwidthMbpsMin()));
        }
        if (criteria.getBandwidthMbpsMax() != null) {
            spec = spec.and(bandwidthMbpsMax(criteria.getBandwidthMbpsMax()));
        }
        if (criteria.getProviderId() != null) {
            spec = spec.and(providerIdEquals(criteria.getProviderId()));
        }
        if (criteria.getASiteId() != null) {
            spec = spec.and(aSiteIdEquals(criteria.getASiteId()));
        }
        if (criteria.getZSiteId() != null) {
            spec = spec.and(zSiteIdEquals(criteria.getZSiteId()));
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
