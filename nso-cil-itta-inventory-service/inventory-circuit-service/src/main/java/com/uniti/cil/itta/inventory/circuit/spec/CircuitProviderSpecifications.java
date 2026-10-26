package com.uniti.cil.itta.inventory.circuit.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitProviderSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CircuitProvider search. */
public final class CircuitProviderSpecifications {

    private CircuitProviderSpecifications() {
    }

    public static Specification<CircuitProvider> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CircuitProvider> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CircuitProvider> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CircuitProvider> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> providerNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("providerName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> accountNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("accountNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> nocContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("noc")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> escalationEmailContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("escalationEmail")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> providerTypeIn(List<CircuitProviderProviderType> values) {
        return (root, query, cb) -> root.get("providerType").in(values);
    }

    public static Specification<CircuitProvider> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> criticalityIn(List<CircuitProviderCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<CircuitProvider> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitProvider> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CircuitProvider> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CircuitProvider> fromCriteria(String tenantId, CircuitProviderSearchCriteria criteria) {
        Specification<CircuitProvider> spec = tenant(tenantId);
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
        if (hasText(criteria.getProviderName())) {
            spec = spec.and(providerNameContains(criteria.getProviderName().trim()));
        }
        if (hasText(criteria.getAccountNumber())) {
            spec = spec.and(accountNumberContains(criteria.getAccountNumber().trim()));
        }
        if (hasText(criteria.getNoc())) {
            spec = spec.and(nocContains(criteria.getNoc().trim()));
        }
        if (hasText(criteria.getEscalationEmail())) {
            spec = spec.and(escalationEmailContains(criteria.getEscalationEmail().trim()));
        }
        if (criteria.getProviderType() != null && !criteria.getProviderType().isEmpty()) {
            spec = spec.and(providerTypeIn(criteria.getProviderType()));
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
