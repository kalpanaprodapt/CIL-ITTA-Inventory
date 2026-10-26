package com.uniti.cil.itta.inventory.device.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.PowerSupplySearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PowerSupply search. */
public final class PowerSupplySpecifications {

    private PowerSupplySpecifications() {
    }

    public static Specification<PowerSupply> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PowerSupply> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PowerSupply> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PowerSupply> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerSupply> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerSupply> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerSupply> chassisIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("chassisId"), value);
    }

    public static Specification<PowerSupply> slotNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<PowerSupply> slotNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<PowerSupply> wattageMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("wattage"), value);
    }

    public static Specification<PowerSupply> wattageMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("wattage"), value);
    }

    public static Specification<PowerSupply> feedIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("feedId"), value);
    }

    public static Specification<PowerSupply> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerSupply> efficiencyMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("efficiency"), value);
    }

    public static Specification<PowerSupply> efficiencyMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("efficiency"), value);
    }

    public static Specification<PowerSupply> criticalityIn(List<PowerSupplyCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<PowerSupply> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerSupply> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PowerSupply> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PowerSupply> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<PowerSupply> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PowerSupply> fromCriteria(String tenantId, PowerSupplySearchCriteria criteria) {
        Specification<PowerSupply> spec = tenant(tenantId);
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
        if (criteria.getChassisId() != null) {
            spec = spec.and(chassisIdEquals(criteria.getChassisId()));
        }
        if (criteria.getSlotNumberMin() != null) {
            spec = spec.and(slotNumberMin(criteria.getSlotNumberMin()));
        }
        if (criteria.getSlotNumberMax() != null) {
            spec = spec.and(slotNumberMax(criteria.getSlotNumberMax()));
        }
        if (criteria.getWattageMin() != null) {
            spec = spec.and(wattageMin(criteria.getWattageMin()));
        }
        if (criteria.getWattageMax() != null) {
            spec = spec.and(wattageMax(criteria.getWattageMax()));
        }
        if (criteria.getFeedId() != null) {
            spec = spec.and(feedIdEquals(criteria.getFeedId()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
        }
        if (criteria.getEfficiencyMin() != null) {
            spec = spec.and(efficiencyMin(criteria.getEfficiencyMin()));
        }
        if (criteria.getEfficiencyMax() != null) {
            spec = spec.and(efficiencyMax(criteria.getEfficiencyMax()));
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
