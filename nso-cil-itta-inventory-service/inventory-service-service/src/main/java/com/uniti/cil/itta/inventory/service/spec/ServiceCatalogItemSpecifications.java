package com.uniti.cil.itta.inventory.service.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceCatalogItemSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ServiceCatalogItem search. */
public final class ServiceCatalogItemSpecifications {

    private ServiceCatalogItemSpecifications() {
    }

    public static Specification<ServiceCatalogItem> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ServiceCatalogItem> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ServiceCatalogItem> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ServiceCatalogItem> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> itemNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("itemName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> familyIn(List<ServiceCatalogItemFamily> values) {
        return (root, query, cb) -> root.get("family").in(values);
    }

    public static Specification<ServiceCatalogItem> basePriceMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("basePrice"), value);
    }

    public static Specification<ServiceCatalogItem> basePriceMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("basePrice"), value);
    }

    public static Specification<ServiceCatalogItem> billingUnitContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("billingUnit")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> orderableEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("orderable"), value);
    }

    public static Specification<ServiceCatalogItem> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ServiceCatalogItem> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ServiceCatalogItem> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> lifecycleStageIn(List<ServiceCatalogItemLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<ServiceCatalogItem> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceCatalogItem> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ServiceCatalogItem> fromCriteria(String tenantId, ServiceCatalogItemSearchCriteria criteria) {
        Specification<ServiceCatalogItem> spec = tenant(tenantId);
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
        if (hasText(criteria.getItemName())) {
            spec = spec.and(itemNameContains(criteria.getItemName().trim()));
        }
        if (criteria.getFamily() != null && !criteria.getFamily().isEmpty()) {
            spec = spec.and(familyIn(criteria.getFamily()));
        }
        if (criteria.getBasePriceMin() != null) {
            spec = spec.and(basePriceMin(criteria.getBasePriceMin()));
        }
        if (criteria.getBasePriceMax() != null) {
            spec = spec.and(basePriceMax(criteria.getBasePriceMax()));
        }
        if (hasText(criteria.getBillingUnit())) {
            spec = spec.and(billingUnitContains(criteria.getBillingUnit().trim()));
        }
        if (criteria.getOrderable() != null) {
            spec = spec.and(orderableEquals(criteria.getOrderable()));
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
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        return spec;
    }
}
