package com.uniti.cil.itta.inventory.topology.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.LinkSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Link search. */
public final class LinkSpecifications {

    private LinkSpecifications() {
    }

    public static Specification<Link> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Link> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Link> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Link> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> aInterfaceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aInterfaceId"), value);
    }

    public static Specification<Link> zInterfaceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zInterfaceId"), value);
    }

    public static Specification<Link> linkTypeIn(List<LinkLinkType> values) {
        return (root, query, cb) -> root.get("linkType").in(values);
    }

    public static Specification<Link> speedMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("speedMbps"), value);
    }

    public static Specification<Link> speedMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("speedMbps"), value);
    }

    public static Specification<Link> discoveredByContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("discoveredBy")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Link> criticalityIn(List<LinkCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Link> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Link> fromCriteria(String tenantId, LinkSearchCriteria criteria) {
        Specification<Link> spec = tenant(tenantId);
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
        if (criteria.getAInterfaceId() != null) {
            spec = spec.and(aInterfaceIdEquals(criteria.getAInterfaceId()));
        }
        if (criteria.getZInterfaceId() != null) {
            spec = spec.and(zInterfaceIdEquals(criteria.getZInterfaceId()));
        }
        if (criteria.getLinkType() != null && !criteria.getLinkType().isEmpty()) {
            spec = spec.and(linkTypeIn(criteria.getLinkType()));
        }
        if (criteria.getSpeedMbpsMin() != null) {
            spec = spec.and(speedMbpsMin(criteria.getSpeedMbpsMin()));
        }
        if (criteria.getSpeedMbpsMax() != null) {
            spec = spec.and(speedMbpsMax(criteria.getSpeedMbpsMax()));
        }
        if (hasText(criteria.getDiscoveredBy())) {
            spec = spec.and(discoveredByContains(criteria.getDiscoveredBy().trim()));
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
