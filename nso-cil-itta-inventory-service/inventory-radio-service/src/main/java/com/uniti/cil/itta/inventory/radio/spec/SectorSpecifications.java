package com.uniti.cil.itta.inventory.radio.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.SectorSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Sector search. */
public final class SectorSpecifications {

    private SectorSpecifications() {
    }

    public static Specification<Sector> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Sector> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Sector> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Sector> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sector> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sector> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sector> baseStationIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("baseStationId"), value);
    }

    public static Specification<Sector> sectorNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("sectorNumber"), value);
    }

    public static Specification<Sector> sectorNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("sectorNumber"), value);
    }

    public static Specification<Sector> pciMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("pci"), value);
    }

    public static Specification<Sector> pciMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("pci"), value);
    }

    public static Specification<Sector> frequencyMhzMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("frequencyMhz"), value);
    }

    public static Specification<Sector> frequencyMhzMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("frequencyMhz"), value);
    }

    public static Specification<Sector> bandwidthMhzMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("bandwidthMhz"), value);
    }

    public static Specification<Sector> bandwidthMhzMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("bandwidthMhz"), value);
    }

    public static Specification<Sector> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sector> criticalityIn(List<SectorCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Sector> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sector> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Sector> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Sector> fromCriteria(String tenantId, SectorSearchCriteria criteria) {
        Specification<Sector> spec = tenant(tenantId);
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
        if (criteria.getBaseStationId() != null) {
            spec = spec.and(baseStationIdEquals(criteria.getBaseStationId()));
        }
        if (criteria.getSectorNumberMin() != null) {
            spec = spec.and(sectorNumberMin(criteria.getSectorNumberMin()));
        }
        if (criteria.getSectorNumberMax() != null) {
            spec = spec.and(sectorNumberMax(criteria.getSectorNumberMax()));
        }
        if (criteria.getPciMin() != null) {
            spec = spec.and(pciMin(criteria.getPciMin()));
        }
        if (criteria.getPciMax() != null) {
            spec = spec.and(pciMax(criteria.getPciMax()));
        }
        if (criteria.getFrequencyMhzMin() != null) {
            spec = spec.and(frequencyMhzMin(criteria.getFrequencyMhzMin()));
        }
        if (criteria.getFrequencyMhzMax() != null) {
            spec = spec.and(frequencyMhzMax(criteria.getFrequencyMhzMax()));
        }
        if (criteria.getBandwidthMhzMin() != null) {
            spec = spec.and(bandwidthMhzMin(criteria.getBandwidthMhzMin()));
        }
        if (criteria.getBandwidthMhzMax() != null) {
            spec = spec.and(bandwidthMhzMax(criteria.getBandwidthMhzMax()));
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
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        return spec;
    }
}
