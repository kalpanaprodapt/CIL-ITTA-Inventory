package com.uniti.cil.itta.inventory.alarm.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.AlarmInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing AlarmInstance search. */
public final class AlarmInstanceSpecifications {

    private AlarmInstanceSpecifications() {
    }

    public static Specification<AlarmInstance> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<AlarmInstance> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<AlarmInstance> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<AlarmInstance> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> definitionIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("definitionId"), value);
    }

    public static Specification<AlarmInstance> sourceRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> raisedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("raisedAt"), value);
    }

    public static Specification<AlarmInstance> raisedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("raisedAt"), value);
    }

    public static Specification<AlarmInstance> clearedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("clearedAt"), value);
    }

    public static Specification<AlarmInstance> clearedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("clearedAt"), value);
    }

    public static Specification<AlarmInstance> ackedByContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ackedBy")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> alarmStateIn(List<AlarmInstanceAlarmState> values) {
        return (root, query, cb) -> root.get("alarmState").in(values);
    }

    public static Specification<AlarmInstance> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<AlarmInstance> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<AlarmInstance> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<AlarmInstance> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<AlarmInstance> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AlarmInstance> lifecycleStageIn(List<AlarmInstanceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<AlarmInstance> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<AlarmInstance> fromCriteria(String tenantId, AlarmInstanceSearchCriteria criteria) {
        Specification<AlarmInstance> spec = tenant(tenantId);
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
        if (criteria.getDefinitionId() != null) {
            spec = spec.and(definitionIdEquals(criteria.getDefinitionId()));
        }
        if (hasText(criteria.getSourceRef())) {
            spec = spec.and(sourceRefContains(criteria.getSourceRef().trim()));
        }
        if (criteria.getRaisedAtFrom() != null) {
            spec = spec.and(raisedAtFrom(criteria.getRaisedAtFrom()));
        }
        if (criteria.getRaisedAtTo() != null) {
            spec = spec.and(raisedAtTo(criteria.getRaisedAtTo()));
        }
        if (criteria.getClearedAtFrom() != null) {
            spec = spec.and(clearedAtFrom(criteria.getClearedAtFrom()));
        }
        if (criteria.getClearedAtTo() != null) {
            spec = spec.and(clearedAtTo(criteria.getClearedAtTo()));
        }
        if (hasText(criteria.getAckedBy())) {
            spec = spec.and(ackedByContains(criteria.getAckedBy().trim()));
        }
        if (criteria.getAlarmState() != null && !criteria.getAlarmState().isEmpty()) {
            spec = spec.and(alarmStateIn(criteria.getAlarmState()));
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
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        return spec;
    }
}
