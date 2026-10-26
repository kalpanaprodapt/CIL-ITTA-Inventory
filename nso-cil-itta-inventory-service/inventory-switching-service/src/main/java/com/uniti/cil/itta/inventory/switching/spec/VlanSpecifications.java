package com.uniti.cil.itta.inventory.switching.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.VlanSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Vlan search. */
public final class VlanSpecifications {

    private VlanSpecifications() {
    }

    public static Specification<Vlan> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Vlan> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Vlan> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Vlan> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> vlanIdMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("vlanId"), value);
    }

    public static Specification<Vlan> vlanIdMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("vlanId"), value);
    }

    public static Specification<Vlan> vlanNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vlanName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> vlanTypeIn(List<VlanVlanType> values) {
        return (root, query, cb) -> root.get("vlanType").in(values);
    }

    public static Specification<Vlan> poolIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("poolId"), value);
    }

    public static Specification<Vlan> stpModeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("stpMode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Vlan> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Vlan> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Vlan> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Vlan> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Vlan> lifecycleStageIn(List<VlanLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Vlan> fromCriteria(String tenantId, VlanSearchCriteria criteria) {
        Specification<Vlan> spec = tenant(tenantId);
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
        if (criteria.getVlanIdMin() != null) {
            spec = spec.and(vlanIdMin(criteria.getVlanIdMin()));
        }
        if (criteria.getVlanIdMax() != null) {
            spec = spec.and(vlanIdMax(criteria.getVlanIdMax()));
        }
        if (hasText(criteria.getVlanName())) {
            spec = spec.and(vlanNameContains(criteria.getVlanName().trim()));
        }
        if (criteria.getVlanType() != null && !criteria.getVlanType().isEmpty()) {
            spec = spec.and(vlanTypeIn(criteria.getVlanType()));
        }
        if (criteria.getPoolId() != null) {
            spec = spec.and(poolIdEquals(criteria.getPoolId()));
        }
        if (hasText(criteria.getStpMode())) {
            spec = spec.and(stpModeContains(criteria.getStpMode().trim()));
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
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        return spec;
    }
}
