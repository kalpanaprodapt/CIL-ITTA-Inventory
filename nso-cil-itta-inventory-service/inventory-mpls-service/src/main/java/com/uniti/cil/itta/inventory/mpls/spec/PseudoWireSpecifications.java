package com.uniti.cil.itta.inventory.mpls.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.PseudoWireSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PseudoWire search. */
public final class PseudoWireSpecifications {

    private PseudoWireSpecifications() {
    }

    public static Specification<PseudoWire> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PseudoWire> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PseudoWire> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PseudoWire> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PseudoWire> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PseudoWire> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PseudoWire> vcIdMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("vcId"), value);
    }

    public static Specification<PseudoWire> vcIdMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("vcId"), value);
    }

    public static Specification<PseudoWire> aEndIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aEndId"), value);
    }

    public static Specification<PseudoWire> zEndIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zEndId"), value);
    }

    public static Specification<PseudoWire> encapsulationContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("encapsulation")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PseudoWire> controlWordEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("controlWord"), value);
    }

    public static Specification<PseudoWire> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PseudoWire> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PseudoWire> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PseudoWire> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<PseudoWire> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<PseudoWire> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PseudoWire> fromCriteria(String tenantId, PseudoWireSearchCriteria criteria) {
        Specification<PseudoWire> spec = tenant(tenantId);
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
        if (criteria.getVcIdMin() != null) {
            spec = spec.and(vcIdMin(criteria.getVcIdMin()));
        }
        if (criteria.getVcIdMax() != null) {
            spec = spec.and(vcIdMax(criteria.getVcIdMax()));
        }
        if (criteria.getAEndId() != null) {
            spec = spec.and(aEndIdEquals(criteria.getAEndId()));
        }
        if (criteria.getZEndId() != null) {
            spec = spec.and(zEndIdEquals(criteria.getZEndId()));
        }
        if (hasText(criteria.getEncapsulation())) {
            spec = spec.and(encapsulationContains(criteria.getEncapsulation().trim()));
        }
        if (criteria.getControlWord() != null) {
            spec = spec.and(controlWordEquals(criteria.getControlWord()));
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
