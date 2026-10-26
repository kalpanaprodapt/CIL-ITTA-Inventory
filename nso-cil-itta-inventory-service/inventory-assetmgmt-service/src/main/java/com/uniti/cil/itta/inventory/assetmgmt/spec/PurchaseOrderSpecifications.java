package com.uniti.cil.itta.inventory.assetmgmt.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.PurchaseOrderSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PurchaseOrder search. */
public final class PurchaseOrderSpecifications {

    private PurchaseOrderSpecifications() {
    }

    public static Specification<PurchaseOrder> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PurchaseOrder> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PurchaseOrder> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PurchaseOrder> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> poNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("poNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<PurchaseOrder> orderedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("orderedOn"), value);
    }

    public static Specification<PurchaseOrder> orderedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("orderedOn"), value);
    }

    public static Specification<PurchaseOrder> totalAmountMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("totalAmount"), value);
    }

    public static Specification<PurchaseOrder> totalAmountMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("totalAmount"), value);
    }

    public static Specification<PurchaseOrder> currencyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("currency")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> criticalityIn(List<PurchaseOrderCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<PurchaseOrder> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PurchaseOrder> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PurchaseOrder> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PurchaseOrder> fromCriteria(String tenantId, PurchaseOrderSearchCriteria criteria) {
        Specification<PurchaseOrder> spec = tenant(tenantId);
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
        if (hasText(criteria.getPoNumber())) {
            spec = spec.and(poNumberContains(criteria.getPoNumber().trim()));
        }
        if (criteria.getVendorId() != null) {
            spec = spec.and(vendorIdEquals(criteria.getVendorId()));
        }
        if (criteria.getOrderedOnFrom() != null) {
            spec = spec.and(orderedOnFrom(criteria.getOrderedOnFrom()));
        }
        if (criteria.getOrderedOnTo() != null) {
            spec = spec.and(orderedOnTo(criteria.getOrderedOnTo()));
        }
        if (criteria.getTotalAmountMin() != null) {
            spec = spec.and(totalAmountMin(criteria.getTotalAmountMin()));
        }
        if (criteria.getTotalAmountMax() != null) {
            spec = spec.and(totalAmountMax(criteria.getTotalAmountMax()));
        }
        if (hasText(criteria.getCurrency())) {
            spec = spec.and(currencyContains(criteria.getCurrency().trim()));
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
