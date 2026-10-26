package com.uniti.cil.itta.inventory.optical.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.OpticalChannelSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing OpticalChannel search. */
public final class OpticalChannelSpecifications {

    private OpticalChannelSpecifications() {
    }

    public static Specification<OpticalChannel> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<OpticalChannel> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<OpticalChannel> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<OpticalChannel> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OpticalChannel> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OpticalChannel> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OpticalChannel> wavelengthNmMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("wavelengthNm"), value);
    }

    public static Specification<OpticalChannel> wavelengthNmMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("wavelengthNm"), value);
    }

    public static Specification<OpticalChannel> channelNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("channelNumber"), value);
    }

    public static Specification<OpticalChannel> channelNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("channelNumber"), value);
    }

    public static Specification<OpticalChannel> gridTypeIn(List<OpticalChannelGridType> values) {
        return (root, query, cb) -> root.get("gridType").in(values);
    }

    public static Specification<OpticalChannel> bitrateGbpsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("bitrateGbps"), value);
    }

    public static Specification<OpticalChannel> bitrateGbpsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("bitrateGbps"), value);
    }

    public static Specification<OpticalChannel> systemIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("systemId"), value);
    }

    public static Specification<OpticalChannel> criticalityIn(List<OpticalChannelCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<OpticalChannel> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<OpticalChannel> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<OpticalChannel> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<OpticalChannel> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<OpticalChannel> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<OpticalChannel> fromCriteria(String tenantId, OpticalChannelSearchCriteria criteria) {
        Specification<OpticalChannel> spec = tenant(tenantId);
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
        if (criteria.getWavelengthNmMin() != null) {
            spec = spec.and(wavelengthNmMin(criteria.getWavelengthNmMin()));
        }
        if (criteria.getWavelengthNmMax() != null) {
            spec = spec.and(wavelengthNmMax(criteria.getWavelengthNmMax()));
        }
        if (criteria.getChannelNumberMin() != null) {
            spec = spec.and(channelNumberMin(criteria.getChannelNumberMin()));
        }
        if (criteria.getChannelNumberMax() != null) {
            spec = spec.and(channelNumberMax(criteria.getChannelNumberMax()));
        }
        if (criteria.getGridType() != null && !criteria.getGridType().isEmpty()) {
            spec = spec.and(gridTypeIn(criteria.getGridType()));
        }
        if (criteria.getBitrateGbpsMin() != null) {
            spec = spec.and(bitrateGbpsMin(criteria.getBitrateGbpsMin()));
        }
        if (criteria.getBitrateGbpsMax() != null) {
            spec = spec.and(bitrateGbpsMax(criteria.getBitrateGbpsMax()));
        }
        if (criteria.getSystemId() != null) {
            spec = spec.and(systemIdEquals(criteria.getSystemId()));
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
