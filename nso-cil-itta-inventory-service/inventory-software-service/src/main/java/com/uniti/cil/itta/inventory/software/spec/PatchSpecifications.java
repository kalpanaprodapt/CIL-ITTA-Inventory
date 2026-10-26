package com.uniti.cil.itta.inventory.software.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.PatchSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Patch search. */
public final class PatchSpecifications {

    private PatchSpecifications() {
    }

    public static Specification<Patch> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Patch> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Patch> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Patch> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Patch> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Patch> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Patch> versionIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("versionId"), value);
    }

    public static Specification<Patch> patchIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("patchId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Patch> severityIn(List<PatchSeverity> values) {
        return (root, query, cb) -> root.get("severity").in(values);
    }

    public static Specification<Patch> releasedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("releasedOn"), value);
    }

    public static Specification<Patch> releasedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("releasedOn"), value);
    }

    public static Specification<Patch> rebootRequiredEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("rebootRequired"), value);
    }

    public static Specification<Patch> criticalityIn(List<PatchCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Patch> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Patch> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Patch> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Patch> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Patch> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Patch> fromCriteria(String tenantId, PatchSearchCriteria criteria) {
        Specification<Patch> spec = tenant(tenantId);
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
        if (criteria.getVersionId() != null) {
            spec = spec.and(versionIdEquals(criteria.getVersionId()));
        }
        if (hasText(criteria.getPatchId())) {
            spec = spec.and(patchIdContains(criteria.getPatchId().trim()));
        }
        if (criteria.getSeverity() != null && !criteria.getSeverity().isEmpty()) {
            spec = spec.and(severityIn(criteria.getSeverity()));
        }
        if (criteria.getReleasedOnFrom() != null) {
            spec = spec.and(releasedOnFrom(criteria.getReleasedOnFrom()));
        }
        if (criteria.getReleasedOnTo() != null) {
            spec = spec.and(releasedOnTo(criteria.getReleasedOnTo()));
        }
        if (criteria.getRebootRequired() != null) {
            spec = spec.and(rebootRequiredEquals(criteria.getRebootRequired()));
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
