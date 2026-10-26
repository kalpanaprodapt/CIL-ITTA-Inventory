package com.uniti.cil.itta.inventory.cabling.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.PatchPanelSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PatchPanel search. */
public final class PatchPanelSpecifications {

    private PatchPanelSpecifications() {
    }

    public static Specification<PatchPanel> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PatchPanel> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PatchPanel> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PatchPanel> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PatchPanel> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PatchPanel> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PatchPanel> roomIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("roomId"), value);
    }

    public static Specification<PatchPanel> rackIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("rackId"), value);
    }

    public static Specification<PatchPanel> panelTypeIn(List<PatchPanelPanelType> values) {
        return (root, query, cb) -> root.get("panelType").in(values);
    }

    public static Specification<PatchPanel> portCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("portCount"), value);
    }

    public static Specification<PatchPanel> portCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("portCount"), value);
    }

    public static Specification<PatchPanel> labelContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("label")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PatchPanel> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PatchPanel> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PatchPanel> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<PatchPanel> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<PatchPanel> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PatchPanel> lifecycleStageIn(List<PatchPanelLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PatchPanel> fromCriteria(String tenantId, PatchPanelSearchCriteria criteria) {
        Specification<PatchPanel> spec = tenant(tenantId);
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
        if (criteria.getRoomId() != null) {
            spec = spec.and(roomIdEquals(criteria.getRoomId()));
        }
        if (criteria.getRackId() != null) {
            spec = spec.and(rackIdEquals(criteria.getRackId()));
        }
        if (criteria.getPanelType() != null && !criteria.getPanelType().isEmpty()) {
            spec = spec.and(panelTypeIn(criteria.getPanelType()));
        }
        if (criteria.getPortCountMin() != null) {
            spec = spec.and(portCountMin(criteria.getPortCountMin()));
        }
        if (criteria.getPortCountMax() != null) {
            spec = spec.and(portCountMax(criteria.getPortCountMax()));
        }
        if (hasText(criteria.getLabel())) {
            spec = spec.and(labelContains(criteria.getLabel().trim()));
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
