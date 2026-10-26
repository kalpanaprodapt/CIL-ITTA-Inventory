package com.uniti.cil.itta.inventory.ipam.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpAddressSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing IpAddress search. */
public final class IpAddressSpecifications {

    private IpAddressSpecifications() {
    }

    public static Specification<IpAddress> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<IpAddress> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<IpAddress> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<IpAddress> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> subnetIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("subnetId"), value);
    }

    public static Specification<IpAddress> addressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> assignedToIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("assignedToId"), value);
    }

    public static Specification<IpAddress> assignmentTypeIn(List<IpAddressAssignmentType> values) {
        return (root, query, cb) -> root.get("assignmentType").in(values);
    }

    public static Specification<IpAddress> dnsNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("dnsName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpAddress> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<IpAddress> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<IpAddress> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<IpAddress> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<IpAddress> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<IpAddress> fromCriteria(String tenantId, IpAddressSearchCriteria criteria) {
        Specification<IpAddress> spec = tenant(tenantId);
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
        if (criteria.getSubnetId() != null) {
            spec = spec.and(subnetIdEquals(criteria.getSubnetId()));
        }
        if (hasText(criteria.getAddress())) {
            spec = spec.and(addressContains(criteria.getAddress().trim()));
        }
        if (criteria.getAssignedToId() != null) {
            spec = spec.and(assignedToIdEquals(criteria.getAssignedToId()));
        }
        if (criteria.getAssignmentType() != null && !criteria.getAssignmentType().isEmpty()) {
            spec = spec.and(assignmentTypeIn(criteria.getAssignmentType()));
        }
        if (hasText(criteria.getDnsName())) {
            spec = spec.and(dnsNameContains(criteria.getDnsName().trim()));
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
