package com.uniti.cil.itta.inventory.optical.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.AmplifierSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Amplifier search. */
public final class AmplifierSpecifications {

    private AmplifierSpecifications() {
    }

    public static Specification<Amplifier> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Amplifier> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Amplifier> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Amplifier> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> systemIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("systemId"), value);
    }

    public static Specification<Amplifier> ampTypeIn(List<AmplifierAmpType> values) {
        return (root, query, cb) -> root.get("ampType").in(values);
    }

    public static Specification<Amplifier> gainDbMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("gainDb"), value);
    }

    public static Specification<Amplifier> gainDbMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("gainDb"), value);
    }

    public static Specification<Amplifier> outputPowerDbmMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("outputPowerDbm"), value);
    }

    public static Specification<Amplifier> outputPowerDbmMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("outputPowerDbm"), value);
    }

    public static Specification<Amplifier> noiseFigureDbMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("noiseFigureDb"), value);
    }

    public static Specification<Amplifier> noiseFigureDbMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("noiseFigureDb"), value);
    }

    public static Specification<Amplifier> lifecycleStageIn(List<AmplifierLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Amplifier> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Amplifier> criticalityIn(List<AmplifierCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Amplifier> fromCriteria(String tenantId, AmplifierSearchCriteria criteria) {
        Specification<Amplifier> spec = tenant(tenantId);
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
        if (criteria.getSystemId() != null) {
            spec = spec.and(systemIdEquals(criteria.getSystemId()));
        }
        if (criteria.getAmpType() != null && !criteria.getAmpType().isEmpty()) {
            spec = spec.and(ampTypeIn(criteria.getAmpType()));
        }
        if (criteria.getGainDbMin() != null) {
            spec = spec.and(gainDbMin(criteria.getGainDbMin()));
        }
        if (criteria.getGainDbMax() != null) {
            spec = spec.and(gainDbMax(criteria.getGainDbMax()));
        }
        if (criteria.getOutputPowerDbmMin() != null) {
            spec = spec.and(outputPowerDbmMin(criteria.getOutputPowerDbmMin()));
        }
        if (criteria.getOutputPowerDbmMax() != null) {
            spec = spec.and(outputPowerDbmMax(criteria.getOutputPowerDbmMax()));
        }
        if (criteria.getNoiseFigureDbMin() != null) {
            spec = spec.and(noiseFigureDbMin(criteria.getNoiseFigureDbMin()));
        }
        if (criteria.getNoiseFigureDbMax() != null) {
            spec = spec.and(noiseFigureDbMax(criteria.getNoiseFigureDbMax()));
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
