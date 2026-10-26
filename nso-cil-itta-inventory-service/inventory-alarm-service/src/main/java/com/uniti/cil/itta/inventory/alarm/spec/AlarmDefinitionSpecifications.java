package com.uniti.cil.itta.inventory.alarm.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.AlarmDefinitionSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing AlarmDefinition search. */
public final class AlarmDefinitionSpecifications {

    private AlarmDefinitionSpecifications() {
    }

    public static Specification<AlarmDefinition> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<AlarmDefinition> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<AlarmDefinition> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<AlarmDefinition> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> alarmCodeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("alarmCode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> severityIn(List<AlarmDefinitionSeverity> values) {
        return (root, query, cb) -> root.get("severity").in(values);
    }

    public static Specification<AlarmDefinition> probableCauseContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("probableCause")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> autoClearEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("autoClear"), value);
    }

    public static Specification<AlarmDefinition> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmDefinition> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<AlarmDefinition> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<AlarmDefinition> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<AlarmDefinition> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<AlarmDefinition> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<AlarmDefinition> fromCriteria(String tenantId, AlarmDefinitionSearchCriteria criteria) {
        Specification<AlarmDefinition> spec = tenant(tenantId);
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
        if (hasText(criteria.getAlarmCode())) {
            spec = spec.and(alarmCodeContains(criteria.getAlarmCode().trim()));
        }
        if (criteria.getSeverity() != null && !criteria.getSeverity().isEmpty()) {
            spec = spec.and(severityIn(criteria.getSeverity()));
        }
        if (hasText(criteria.getProbableCause())) {
            spec = spec.and(probableCauseContains(criteria.getProbableCause().trim()));
        }
        if (criteria.getAutoClear() != null) {
            spec = spec.and(autoClearEquals(criteria.getAutoClear()));
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
