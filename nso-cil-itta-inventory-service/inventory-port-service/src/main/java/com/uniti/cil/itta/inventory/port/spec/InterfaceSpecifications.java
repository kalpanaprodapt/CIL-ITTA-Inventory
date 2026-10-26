package com.uniti.cil.itta.inventory.port.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.InterfaceSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Interface search. */
public final class InterfaceSpecifications {

    private InterfaceSpecifications() {
    }

    public static Specification<Interface> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Interface> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Interface> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Interface> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> portIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("portId"), value);
    }

    public static Specification<Interface> ifNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ifName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> ifIndexMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("ifIndex"), value);
    }

    public static Specification<Interface> ifIndexMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("ifIndex"), value);
    }

    public static Specification<Interface> macAddressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("macAddress")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> mtuMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("mtu"), value);
    }

    public static Specification<Interface> mtuMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("mtu"), value);
    }

    public static Specification<Interface> operStateIn(List<InterfaceOperState> values) {
        return (root, query, cb) -> root.get("operState").in(values);
    }

    public static Specification<Interface> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> criticalityIn(List<InterfaceCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Interface> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Interface> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Interface> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Interface> fromCriteria(String tenantId, InterfaceSearchCriteria criteria) {
        Specification<Interface> spec = tenant(tenantId);
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
        if (criteria.getPortId() != null) {
            spec = spec.and(portIdEquals(criteria.getPortId()));
        }
        if (hasText(criteria.getIfName())) {
            spec = spec.and(ifNameContains(criteria.getIfName().trim()));
        }
        if (criteria.getIfIndexMin() != null) {
            spec = spec.and(ifIndexMin(criteria.getIfIndexMin()));
        }
        if (criteria.getIfIndexMax() != null) {
            spec = spec.and(ifIndexMax(criteria.getIfIndexMax()));
        }
        if (hasText(criteria.getMacAddress())) {
            spec = spec.and(macAddressContains(criteria.getMacAddress().trim()));
        }
        if (criteria.getMtuMin() != null) {
            spec = spec.and(mtuMin(criteria.getMtuMin()));
        }
        if (criteria.getMtuMax() != null) {
            spec = spec.and(mtuMax(criteria.getMtuMax()));
        }
        if (criteria.getOperState() != null && !criteria.getOperState().isEmpty()) {
            spec = spec.and(operStateIn(criteria.getOperState()));
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
