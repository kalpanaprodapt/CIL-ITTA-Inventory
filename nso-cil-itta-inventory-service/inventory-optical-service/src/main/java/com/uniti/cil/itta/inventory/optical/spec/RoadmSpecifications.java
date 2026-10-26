package com.uniti.cil.itta.inventory.optical.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.RoadmSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Roadm search. */
public final class RoadmSpecifications {

    private RoadmSpecifications() {
    }

    public static Specification<Roadm> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Roadm> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Roadm> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Roadm> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Roadm> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Roadm> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Roadm> systemIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("systemId"), value);
    }

    public static Specification<Roadm> degreesMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("degrees"), value);
    }

    public static Specification<Roadm> degreesMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("degrees"), value);
    }

    public static Specification<Roadm> colorlessEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("colorless"), value);
    }

    public static Specification<Roadm> directionlessEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("directionless"), value);
    }

    public static Specification<Roadm> contentionlessEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("contentionless"), value);
    }

    public static Specification<Roadm> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Roadm> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Roadm> criticalityIn(List<RoadmCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Roadm> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Roadm> fromCriteria(String tenantId, RoadmSearchCriteria criteria) {
        Specification<Roadm> spec = tenant(tenantId);
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
        if (criteria.getSystemId() != null) {
            spec = spec.and(systemIdEquals(criteria.getSystemId()));
        }
        if (criteria.getDegreesMin() != null) {
            spec = spec.and(degreesMin(criteria.getDegreesMin()));
        }
        if (criteria.getDegreesMax() != null) {
            spec = spec.and(degreesMax(criteria.getDegreesMax()));
        }
        if (criteria.getColorless() != null) {
            spec = spec.and(colorlessEquals(criteria.getColorless()));
        }
        if (criteria.getDirectionless() != null) {
            spec = spec.and(directionlessEquals(criteria.getDirectionless()));
        }
        if (criteria.getContentionless() != null) {
            spec = spec.and(contentionlessEquals(criteria.getContentionless()));
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
