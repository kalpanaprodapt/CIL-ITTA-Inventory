package com.uniti.cil.itta.inventory.device.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.LineCardSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing LineCard search. */
public final class LineCardSpecifications {

    private LineCardSpecifications() {
    }

    public static Specification<LineCard> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<LineCard> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<LineCard> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<LineCard> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> chassisIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("chassisId"), value);
    }

    public static Specification<LineCard> slotNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<LineCard> slotNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<LineCard> cardTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("cardType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> portCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("portCount"), value);
    }

    public static Specification<LineCard> portCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("portCount"), value);
    }

    public static Specification<LineCard> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> firmwareContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("firmware")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LineCard> criticalityIn(List<LineCardCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<LineCard> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<LineCard> fromCriteria(String tenantId, LineCardSearchCriteria criteria) {
        Specification<LineCard> spec = tenant(tenantId);
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
        if (hasText(criteria.getCardType())) {
            spec = spec.and(cardTypeContains(criteria.getCardType().trim()));
        }
        if (criteria.getPortCountMin() != null) {
            spec = spec.and(portCountMin(criteria.getPortCountMin()));
        }
        if (criteria.getPortCountMax() != null) {
            spec = spec.and(portCountMax(criteria.getPortCountMax()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
        }
        if (hasText(criteria.getFirmware())) {
            spec = spec.and(firmwareContains(criteria.getFirmware().trim()));
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
