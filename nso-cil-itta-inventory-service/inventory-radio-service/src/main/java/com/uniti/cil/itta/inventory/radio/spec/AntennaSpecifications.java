package com.uniti.cil.itta.inventory.radio.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.AntennaSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Antenna search. */
public final class AntennaSpecifications {

    private AntennaSpecifications() {
    }

    public static Specification<Antenna> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Antenna> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Antenna> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Antenna> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> sectorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("sectorId"), value);
    }

    public static Specification<Antenna> modelContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("model")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> heightMMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("heightM"), value);
    }

    public static Specification<Antenna> heightMMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("heightM"), value);
    }

    public static Specification<Antenna> azimuthDegMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("azimuthDeg"), value);
    }

    public static Specification<Antenna> azimuthDegMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("azimuthDeg"), value);
    }

    public static Specification<Antenna> tiltDegMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("tiltDeg"), value);
    }

    public static Specification<Antenna> tiltDegMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("tiltDeg"), value);
    }

    public static Specification<Antenna> gainDbiMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("gainDbi"), value);
    }

    public static Specification<Antenna> gainDbiMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("gainDbi"), value);
    }

    public static Specification<Antenna> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Antenna> criticalityIn(List<AntennaCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Antenna> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Antenna> fromCriteria(String tenantId, AntennaSearchCriteria criteria) {
        Specification<Antenna> spec = tenant(tenantId);
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
        if (criteria.getSectorId() != null) {
            spec = spec.and(sectorIdEquals(criteria.getSectorId()));
        }
        if (hasText(criteria.getModel())) {
            spec = spec.and(modelContains(criteria.getModel().trim()));
        }
        if (criteria.getHeightMMin() != null) {
            spec = spec.and(heightMMin(criteria.getHeightMMin()));
        }
        if (criteria.getHeightMMax() != null) {
            spec = spec.and(heightMMax(criteria.getHeightMMax()));
        }
        if (criteria.getAzimuthDegMin() != null) {
            spec = spec.and(azimuthDegMin(criteria.getAzimuthDegMin()));
        }
        if (criteria.getAzimuthDegMax() != null) {
            spec = spec.and(azimuthDegMax(criteria.getAzimuthDegMax()));
        }
        if (criteria.getTiltDegMin() != null) {
            spec = spec.and(tiltDegMin(criteria.getTiltDegMin()));
        }
        if (criteria.getTiltDegMax() != null) {
            spec = spec.and(tiltDegMax(criteria.getTiltDegMax()));
        }
        if (criteria.getGainDbiMin() != null) {
            spec = spec.and(gainDbiMin(criteria.getGainDbiMin()));
        }
        if (criteria.getGainDbiMax() != null) {
            spec = spec.and(gainDbiMax(criteria.getGainDbiMax()));
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
