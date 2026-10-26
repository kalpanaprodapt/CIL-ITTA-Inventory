package com.uniti.cil.itta.inventory.vendor.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.SupportContractSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SupportContract search. */
public final class SupportContractSpecifications {

    private SupportContractSpecifications() {
    }

    public static Specification<SupportContract> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SupportContract> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SupportContract> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SupportContract> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<SupportContract> contractNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("contractNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> startDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<SupportContract> startDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<SupportContract> endDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("endDate"), value);
    }

    public static Specification<SupportContract> endDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("endDate"), value);
    }

    public static Specification<SupportContract> coverageLevelIn(List<SupportContractCoverageLevel> values) {
        return (root, query, cb) -> root.get("coverageLevel").in(values);
    }

    public static Specification<SupportContract> annualCostMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("annualCost"), value);
    }

    public static Specification<SupportContract> annualCostMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("annualCost"), value);
    }

    public static Specification<SupportContract> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> criticalityIn(List<SupportContractCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<SupportContract> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SupportContract> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SupportContract> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SupportContract> fromCriteria(String tenantId, SupportContractSearchCriteria criteria) {
        Specification<SupportContract> spec = tenant(tenantId);
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
        if (criteria.getVendorId() != null) {
            spec = spec.and(vendorIdEquals(criteria.getVendorId()));
        }
        if (hasText(criteria.getContractNumber())) {
            spec = spec.and(contractNumberContains(criteria.getContractNumber().trim()));
        }
        if (criteria.getStartDateFrom() != null) {
            spec = spec.and(startDateFrom(criteria.getStartDateFrom()));
        }
        if (criteria.getStartDateTo() != null) {
            spec = spec.and(startDateTo(criteria.getStartDateTo()));
        }
        if (criteria.getEndDateFrom() != null) {
            spec = spec.and(endDateFrom(criteria.getEndDateFrom()));
        }
        if (criteria.getEndDateTo() != null) {
            spec = spec.and(endDateTo(criteria.getEndDateTo()));
        }
        if (criteria.getCoverageLevel() != null && !criteria.getCoverageLevel().isEmpty()) {
            spec = spec.and(coverageLevelIn(criteria.getCoverageLevel()));
        }
        if (criteria.getAnnualCostMin() != null) {
            spec = spec.and(annualCostMin(criteria.getAnnualCostMin()));
        }
        if (criteria.getAnnualCostMax() != null) {
            spec = spec.and(annualCostMax(criteria.getAnnualCostMax()));
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
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        return spec;
    }
}
