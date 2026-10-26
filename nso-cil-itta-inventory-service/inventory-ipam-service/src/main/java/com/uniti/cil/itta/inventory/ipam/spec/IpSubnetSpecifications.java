package com.uniti.cil.itta.inventory.ipam.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpSubnetSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing IpSubnet search. */
public final class IpSubnetSpecifications {

    private IpSubnetSpecifications() {
    }

    public static Specification<IpSubnet> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<IpSubnet> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<IpSubnet> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<IpSubnet> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> cidrContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("cidr")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> ipVersionIn(List<IpSubnetIpVersion> values) {
        return (root, query, cb) -> root.get("ipVersion").in(values);
    }

    public static Specification<IpSubnet> vrfIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vrfId"), value);
    }

    public static Specification<IpSubnet> gatewayContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("gateway")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> vlanIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vlanId"), value);
    }

    public static Specification<IpSubnet> utilizationPctMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("utilizationPct"), value);
    }

    public static Specification<IpSubnet> utilizationPctMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("utilizationPct"), value);
    }

    public static Specification<IpSubnet> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> criticalityIn(List<IpSubnetCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<IpSubnet> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpSubnet> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<IpSubnet> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<IpSubnet> fromCriteria(String tenantId, IpSubnetSearchCriteria criteria) {
        Specification<IpSubnet> spec = tenant(tenantId);
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
        if (hasText(criteria.getCidr())) {
            spec = spec.and(cidrContains(criteria.getCidr().trim()));
        }
        if (criteria.getIpVersion() != null && !criteria.getIpVersion().isEmpty()) {
            spec = spec.and(ipVersionIn(criteria.getIpVersion()));
        }
        if (criteria.getVrfId() != null) {
            spec = spec.and(vrfIdEquals(criteria.getVrfId()));
        }
        if (hasText(criteria.getGateway())) {
            spec = spec.and(gatewayContains(criteria.getGateway().trim()));
        }
        if (criteria.getVlanId() != null) {
            spec = spec.and(vlanIdEquals(criteria.getVlanId()));
        }
        if (criteria.getUtilizationPctMin() != null) {
            spec = spec.and(utilizationPctMin(criteria.getUtilizationPctMin()));
        }
        if (criteria.getUtilizationPctMax() != null) {
            spec = spec.and(utilizationPctMax(criteria.getUtilizationPctMax()));
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
