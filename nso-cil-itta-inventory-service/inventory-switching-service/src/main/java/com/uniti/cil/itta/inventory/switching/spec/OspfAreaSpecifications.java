package com.uniti.cil.itta.inventory.switching.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.OspfAreaSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing OspfArea search. */
public final class OspfAreaSpecifications {

    private OspfAreaSpecifications() {
    }

    public static Specification<OspfArea> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<OspfArea> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<OspfArea> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<OspfArea> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> areaIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("areaId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> areaTypeIn(List<OspfAreaAreaType> values) {
        return (root, query, cb) -> root.get("areaType").in(values);
    }

    public static Specification<OspfArea> deviceCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("deviceCount"), value);
    }

    public static Specification<OspfArea> deviceCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("deviceCount"), value);
    }

    public static Specification<OspfArea> authTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("authType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> criticalityIn(List<OspfAreaCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<OspfArea> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OspfArea> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<OspfArea> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<OspfArea> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<OspfArea> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<OspfArea> fromCriteria(String tenantId, OspfAreaSearchCriteria criteria) {
        Specification<OspfArea> spec = tenant(tenantId);
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
        if (hasText(criteria.getAreaId())) {
            spec = spec.and(areaIdContains(criteria.getAreaId().trim()));
        }
        if (criteria.getAreaType() != null && !criteria.getAreaType().isEmpty()) {
            spec = spec.and(areaTypeIn(criteria.getAreaType()));
        }
        if (criteria.getDeviceCountMin() != null) {
            spec = spec.and(deviceCountMin(criteria.getDeviceCountMin()));
        }
        if (criteria.getDeviceCountMax() != null) {
            spec = spec.and(deviceCountMax(criteria.getDeviceCountMax()));
        }
        if (hasText(criteria.getAuthType())) {
            spec = spec.and(authTypeContains(criteria.getAuthType().trim()));
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
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
        }
        return spec;
    }
}
