package com.uniti.cil.itta.inventory.optical.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.OtnLinkSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing OtnLink search. */
public final class OtnLinkSpecifications {

    private OtnLinkSpecifications() {
    }

    public static Specification<OtnLink> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<OtnLink> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<OtnLink> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<OtnLink> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> aNodeIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aNodeId"), value);
    }

    public static Specification<OtnLink> zNodeIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zNodeId"), value);
    }

    public static Specification<OtnLink> oduIn(List<OtnLinkOdu> values) {
        return (root, query, cb) -> root.get("odu").in(values);
    }

    public static Specification<OtnLink> latencyUsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latencyUs"), value);
    }

    public static Specification<OtnLink> latencyUsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latencyUs"), value);
    }

    public static Specification<OtnLink> protectionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("protection")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<OtnLink> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<OtnLink> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> lifecycleStageIn(List<OtnLinkLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<OtnLink> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OtnLink> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<OtnLink> fromCriteria(String tenantId, OtnLinkSearchCriteria criteria) {
        Specification<OtnLink> spec = tenant(tenantId);
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
        if (criteria.getANodeId() != null) {
            spec = spec.and(aNodeIdEquals(criteria.getANodeId()));
        }
        if (criteria.getZNodeId() != null) {
            spec = spec.and(zNodeIdEquals(criteria.getZNodeId()));
        }
        if (criteria.getOdu() != null && !criteria.getOdu().isEmpty()) {
            spec = spec.and(oduIn(criteria.getOdu()));
        }
        if (criteria.getLatencyUsMin() != null) {
            spec = spec.and(latencyUsMin(criteria.getLatencyUsMin()));
        }
        if (criteria.getLatencyUsMax() != null) {
            spec = spec.and(latencyUsMax(criteria.getLatencyUsMax()));
        }
        if (hasText(criteria.getProtection())) {
            spec = spec.and(protectionContains(criteria.getProtection().trim()));
        }
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
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
        return spec;
    }
}
