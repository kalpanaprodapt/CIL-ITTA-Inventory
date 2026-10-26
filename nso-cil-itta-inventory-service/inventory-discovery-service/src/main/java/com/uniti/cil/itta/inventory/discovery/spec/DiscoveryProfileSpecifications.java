package com.uniti.cil.itta.inventory.discovery.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryProfileSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing DiscoveryProfile search. */
public final class DiscoveryProfileSpecifications {

    private DiscoveryProfileSpecifications() {
    }

    public static Specification<DiscoveryProfile> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<DiscoveryProfile> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<DiscoveryProfile> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<DiscoveryProfile> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> profileNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("profileName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> protocolIn(List<DiscoveryProfileProtocol> values) {
        return (root, query, cb) -> root.get("protocol").in(values);
    }

    public static Specification<DiscoveryProfile> credentialRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("credentialRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> scopeCidrContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scopeCidr")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> scheduleCronContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scheduleCron")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryProfile> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<DiscoveryProfile> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<DiscoveryProfile> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<DiscoveryProfile> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<DiscoveryProfile> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<DiscoveryProfile> fromCriteria(String tenantId, DiscoveryProfileSearchCriteria criteria) {
        Specification<DiscoveryProfile> spec = tenant(tenantId);
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
        if (hasText(criteria.getProfileName())) {
            spec = spec.and(profileNameContains(criteria.getProfileName().trim()));
        }
        if (criteria.getProtocol() != null && !criteria.getProtocol().isEmpty()) {
            spec = spec.and(protocolIn(criteria.getProtocol()));
        }
        if (hasText(criteria.getCredentialRef())) {
            spec = spec.and(credentialRefContains(criteria.getCredentialRef().trim()));
        }
        if (hasText(criteria.getScopeCidr())) {
            spec = spec.and(scopeCidrContains(criteria.getScopeCidr().trim()));
        }
        if (hasText(criteria.getScheduleCron())) {
            spec = spec.and(scheduleCronContains(criteria.getScheduleCron().trim()));
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
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
        }
        return spec;
    }
}
