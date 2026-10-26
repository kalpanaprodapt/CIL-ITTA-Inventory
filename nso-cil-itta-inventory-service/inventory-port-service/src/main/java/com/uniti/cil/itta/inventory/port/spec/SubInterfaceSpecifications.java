package com.uniti.cil.itta.inventory.port.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.SubInterfaceSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SubInterface search. */
public final class SubInterfaceSpecifications {

    private SubInterfaceSpecifications() {
    }

    public static Specification<SubInterface> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SubInterface> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SubInterface> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SubInterface> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> interfaceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("interfaceId"), value);
    }

    public static Specification<SubInterface> vlanTagMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("vlanTag"), value);
    }

    public static Specification<SubInterface> vlanTagMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("vlanTag"), value);
    }

    public static Specification<SubInterface> encapsulationContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("encapsulation")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> ipv4AddressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ipv4Address")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> ipv6AddressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ipv6Address")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SubInterface> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SubInterface> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SubInterface> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<SubInterface> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<SubInterface> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SubInterface> fromCriteria(String tenantId, SubInterfaceSearchCriteria criteria) {
        Specification<SubInterface> spec = tenant(tenantId);
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
        if (criteria.getInterfaceId() != null) {
            spec = spec.and(interfaceIdEquals(criteria.getInterfaceId()));
        }
        if (criteria.getVlanTagMin() != null) {
            spec = spec.and(vlanTagMin(criteria.getVlanTagMin()));
        }
        if (criteria.getVlanTagMax() != null) {
            spec = spec.and(vlanTagMax(criteria.getVlanTagMax()));
        }
        if (hasText(criteria.getEncapsulation())) {
            spec = spec.and(encapsulationContains(criteria.getEncapsulation().trim()));
        }
        if (hasText(criteria.getIpv4Address())) {
            spec = spec.and(ipv4AddressContains(criteria.getIpv4Address().trim()));
        }
        if (hasText(criteria.getIpv6Address())) {
            spec = spec.and(ipv6AddressContains(criteria.getIpv6Address().trim()));
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
