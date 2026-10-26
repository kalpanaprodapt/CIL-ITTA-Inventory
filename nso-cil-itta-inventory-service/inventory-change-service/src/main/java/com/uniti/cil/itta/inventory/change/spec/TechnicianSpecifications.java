package com.uniti.cil.itta.inventory.change.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.TechnicianSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Technician search. */
public final class TechnicianSpecifications {

    private TechnicianSpecifications() {
    }

    public static Specification<Technician> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Technician> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Technician> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Technician> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> employeeIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("employeeId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> fullNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("fullName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> skillSetContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("skillSet")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> homeSiteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("homeSiteId"), value);
    }

    public static Specification<Technician> certificationLevelMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("certificationLevel"), value);
    }

    public static Specification<Technician> certificationLevelMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("certificationLevel"), value);
    }

    public static Specification<Technician> criticalityIn(List<TechnicianCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Technician> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Technician> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Technician> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Technician> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Technician> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Technician> fromCriteria(String tenantId, TechnicianSearchCriteria criteria) {
        Specification<Technician> spec = tenant(tenantId);
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
        if (hasText(criteria.getEmployeeId())) {
            spec = spec.and(employeeIdContains(criteria.getEmployeeId().trim()));
        }
        if (hasText(criteria.getFullName())) {
            spec = spec.and(fullNameContains(criteria.getFullName().trim()));
        }
        if (hasText(criteria.getSkillSet())) {
            spec = spec.and(skillSetContains(criteria.getSkillSet().trim()));
        }
        if (criteria.getHomeSiteId() != null) {
            spec = spec.and(homeSiteIdEquals(criteria.getHomeSiteId()));
        }
        if (criteria.getCertificationLevelMin() != null) {
            spec = spec.and(certificationLevelMin(criteria.getCertificationLevelMin()));
        }
        if (criteria.getCertificationLevelMax() != null) {
            spec = spec.and(certificationLevelMax(criteria.getCertificationLevelMax()));
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
