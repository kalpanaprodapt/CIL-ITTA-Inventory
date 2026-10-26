package com.uniti.cil.itta.inventory.cabling.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.SpliceSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Splice search. */
public final class SpliceSpecifications {

    private SpliceSpecifications() {
    }

    public static Specification<Splice> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Splice> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Splice> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Splice> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> enclosureIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("enclosureId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> aStrandIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aStrandId"), value);
    }

    public static Specification<Splice> zStrandIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zStrandId"), value);
    }

    public static Specification<Splice> lossDbMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("lossDb"), value);
    }

    public static Specification<Splice> lossDbMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("lossDb"), value);
    }

    public static Specification<Splice> splicedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("splicedOn"), value);
    }

    public static Specification<Splice> splicedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("splicedOn"), value);
    }

    public static Specification<Splice> lifecycleStageIn(List<SpliceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Splice> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Splice> criticalityIn(List<SpliceCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Splice> fromCriteria(String tenantId, SpliceSearchCriteria criteria) {
        Specification<Splice> spec = tenant(tenantId);
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
        if (hasText(criteria.getEnclosureId())) {
            spec = spec.and(enclosureIdContains(criteria.getEnclosureId().trim()));
        }
        if (criteria.getAStrandId() != null) {
            spec = spec.and(aStrandIdEquals(criteria.getAStrandId()));
        }
        if (criteria.getZStrandId() != null) {
            spec = spec.and(zStrandIdEquals(criteria.getZStrandId()));
        }
        if (criteria.getLossDbMin() != null) {
            spec = spec.and(lossDbMin(criteria.getLossDbMin()));
        }
        if (criteria.getLossDbMax() != null) {
            spec = spec.and(lossDbMax(criteria.getLossDbMax()));
        }
        if (criteria.getSplicedOnFrom() != null) {
            spec = spec.and(splicedOnFrom(criteria.getSplicedOnFrom()));
        }
        if (criteria.getSplicedOnTo() != null) {
            spec = spec.and(splicedOnTo(criteria.getSplicedOnTo()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
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
        return spec;
    }
}
