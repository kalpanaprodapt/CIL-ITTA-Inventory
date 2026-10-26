package com.uniti.cil.itta.inventory.discovery.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationResultSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ReconciliationResult search. */
public final class ReconciliationResultSpecifications {

    private ReconciliationResultSpecifications() {
    }

    public static Specification<ReconciliationResult> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ReconciliationResult> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ReconciliationResult> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ReconciliationResult> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> jobIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("jobId"), value);
    }

    public static Specification<ReconciliationResult> ruleIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("ruleId"), value);
    }

    public static Specification<ReconciliationResult> outcomeIn(List<ReconciliationResultOutcome> values) {
        return (root, query, cb) -> root.get("outcome").in(values);
    }

    public static Specification<ReconciliationResult> resolvedByContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("resolvedBy")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationResult> criticalityIn(List<ReconciliationResultCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ReconciliationResult> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ReconciliationResult> fromCriteria(String tenantId, ReconciliationResultSearchCriteria criteria) {
        Specification<ReconciliationResult> spec = tenant(tenantId);
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
        if (criteria.getJobId() != null) {
            spec = spec.and(jobIdEquals(criteria.getJobId()));
        }
        if (criteria.getRuleId() != null) {
            spec = spec.and(ruleIdEquals(criteria.getRuleId()));
        }
        if (criteria.getOutcome() != null && !criteria.getOutcome().isEmpty()) {
            spec = spec.and(outcomeIn(criteria.getOutcome()));
        }
        if (hasText(criteria.getResolvedBy())) {
            spec = spec.and(resolvedByContains(criteria.getResolvedBy().trim()));
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
