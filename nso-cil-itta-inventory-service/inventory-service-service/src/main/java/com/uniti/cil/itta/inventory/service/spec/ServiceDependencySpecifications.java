package com.uniti.cil.itta.inventory.service.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceDependencySearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ServiceDependency search. */
public final class ServiceDependencySpecifications {

    private ServiceDependencySpecifications() {
    }

    public static Specification<ServiceDependency> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ServiceDependency> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ServiceDependency> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ServiceDependency> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceDependency> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceDependency> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceDependency> serviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("serviceId"), value);
    }

    public static Specification<ServiceDependency> dependsOnIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("dependsOnId"), value);
    }

    public static Specification<ServiceDependency> dependencyTypeIn(List<ServiceDependencyDependencyType> values) {
        return (root, query, cb) -> root.get("dependencyType").in(values);
    }

    public static Specification<ServiceDependency> impactScoreMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("impactScore"), value);
    }

    public static Specification<ServiceDependency> impactScoreMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("impactScore"), value);
    }

    public static Specification<ServiceDependency> criticalityIn(List<ServiceDependencyCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ServiceDependency> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceDependency> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ServiceDependency> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ServiceDependency> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ServiceDependency> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ServiceDependency> fromCriteria(String tenantId, ServiceDependencySearchCriteria criteria) {
        Specification<ServiceDependency> spec = tenant(tenantId);
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
        if (criteria.getServiceId() != null) {
            spec = spec.and(serviceIdEquals(criteria.getServiceId()));
        }
        if (criteria.getDependsOnId() != null) {
            spec = spec.and(dependsOnIdEquals(criteria.getDependsOnId()));
        }
        if (criteria.getDependencyType() != null && !criteria.getDependencyType().isEmpty()) {
            spec = spec.and(dependencyTypeIn(criteria.getDependencyType()));
        }
        if (criteria.getImpactScoreMin() != null) {
            spec = spec.and(impactScoreMin(criteria.getImpactScoreMin()));
        }
        if (criteria.getImpactScoreMax() != null) {
            spec = spec.and(impactScoreMax(criteria.getImpactScoreMax()));
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
