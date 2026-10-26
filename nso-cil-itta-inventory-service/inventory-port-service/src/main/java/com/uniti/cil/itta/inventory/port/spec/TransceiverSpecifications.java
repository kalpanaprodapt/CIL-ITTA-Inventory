package com.uniti.cil.itta.inventory.port.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.TransceiverSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Transceiver search. */
public final class TransceiverSpecifications {

    private TransceiverSpecifications() {
    }

    public static Specification<Transceiver> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Transceiver> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Transceiver> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Transceiver> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> portIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("portId"), value);
    }

    public static Specification<Transceiver> formFactorIn(List<TransceiverFormFactor> values) {
        return (root, query, cb) -> root.get("formFactor").in(values);
    }

    public static Specification<Transceiver> wavelengthNmMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("wavelengthNm"), value);
    }

    public static Specification<Transceiver> wavelengthNmMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("wavelengthNm"), value);
    }

    public static Specification<Transceiver> reachKmMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("reachKm"), value);
    }

    public static Specification<Transceiver> reachKmMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("reachKm"), value);
    }

    public static Specification<Transceiver> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> vendorPartContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("vendorPart")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> lifecycleStageIn(List<TransceiverLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Transceiver> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Transceiver> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Transceiver> fromCriteria(String tenantId, TransceiverSearchCriteria criteria) {
        Specification<Transceiver> spec = tenant(tenantId);
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
        if (criteria.getPortId() != null) {
            spec = spec.and(portIdEquals(criteria.getPortId()));
        }
        if (criteria.getFormFactor() != null && !criteria.getFormFactor().isEmpty()) {
            spec = spec.and(formFactorIn(criteria.getFormFactor()));
        }
        if (criteria.getWavelengthNmMin() != null) {
            spec = spec.and(wavelengthNmMin(criteria.getWavelengthNmMin()));
        }
        if (criteria.getWavelengthNmMax() != null) {
            spec = spec.and(wavelengthNmMax(criteria.getWavelengthNmMax()));
        }
        if (criteria.getReachKmMin() != null) {
            spec = spec.and(reachKmMin(criteria.getReachKmMin()));
        }
        if (criteria.getReachKmMax() != null) {
            spec = spec.and(reachKmMax(criteria.getReachKmMax()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
        }
        if (hasText(criteria.getVendorPart())) {
            spec = spec.and(vendorPartContains(criteria.getVendorPart().trim()));
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
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        return spec;
    }
}
