package com.uniti.cil.itta.inventory.mpls.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.MplsLspSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing MplsLsp search. */
public final class MplsLspSpecifications {

    private MplsLspSpecifications() {
    }

    public static Specification<MplsLsp> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<MplsLsp> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<MplsLsp> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<MplsLsp> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> lspNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("lspName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> headEndIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("headEndId"), value);
    }

    public static Specification<MplsLsp> tailEndIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("tailEndId"), value);
    }

    public static Specification<MplsLsp> bandwidthMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<MplsLsp> bandwidthMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<MplsLsp> setupPriorityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("setupPriority"), value);
    }

    public static Specification<MplsLsp> setupPriorityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("setupPriority"), value);
    }

    public static Specification<MplsLsp> pathTypeIn(List<MplsLspPathType> values) {
        return (root, query, cb) -> root.get("pathType").in(values);
    }

    public static Specification<MplsLsp> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MplsLsp> criticalityIn(List<MplsLspCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<MplsLsp> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<MplsLsp> fromCriteria(String tenantId, MplsLspSearchCriteria criteria) {
        Specification<MplsLsp> spec = tenant(tenantId);
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
        if (hasText(criteria.getLspName())) {
            spec = spec.and(lspNameContains(criteria.getLspName().trim()));
        }
        if (criteria.getHeadEndId() != null) {
            spec = spec.and(headEndIdEquals(criteria.getHeadEndId()));
        }
        if (criteria.getTailEndId() != null) {
            spec = spec.and(tailEndIdEquals(criteria.getTailEndId()));
        }
        if (criteria.getBandwidthMbpsMin() != null) {
            spec = spec.and(bandwidthMbpsMin(criteria.getBandwidthMbpsMin()));
        }
        if (criteria.getBandwidthMbpsMax() != null) {
            spec = spec.and(bandwidthMbpsMax(criteria.getBandwidthMbpsMax()));
        }
        if (criteria.getSetupPriorityMin() != null) {
            spec = spec.and(setupPriorityMin(criteria.getSetupPriorityMin()));
        }
        if (criteria.getSetupPriorityMax() != null) {
            spec = spec.and(setupPriorityMax(criteria.getSetupPriorityMax()));
        }
        if (criteria.getPathType() != null && !criteria.getPathType().isEmpty()) {
            spec = spec.and(pathTypeIn(criteria.getPathType()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
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
