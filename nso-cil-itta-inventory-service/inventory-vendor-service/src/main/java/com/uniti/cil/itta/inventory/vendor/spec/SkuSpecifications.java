package com.uniti.cil.itta.inventory.vendor.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.SkuSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Sku search. */
public final class SkuSpecifications {

    private SkuSpecifications() {
    }

    public static Specification<Sku> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Sku> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Sku> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Sku> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> modelIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("modelId"), value);
    }

    public static Specification<Sku> skuCodeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("skuCode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> listPriceMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("listPrice"), value);
    }

    public static Specification<Sku> listPriceMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("listPrice"), value);
    }

    public static Specification<Sku> currencyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("currency")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> leadTimeDaysMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("leadTimeDays"), value);
    }

    public static Specification<Sku> leadTimeDaysMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("leadTimeDays"), value);
    }

    public static Specification<Sku> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Sku> criticalityIn(List<SkuCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Sku> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Sku> fromCriteria(String tenantId, SkuSearchCriteria criteria) {
        Specification<Sku> spec = tenant(tenantId);
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
        if (criteria.getModelId() != null) {
            spec = spec.and(modelIdEquals(criteria.getModelId()));
        }
        if (hasText(criteria.getSkuCode())) {
            spec = spec.and(skuCodeContains(criteria.getSkuCode().trim()));
        }
        if (criteria.getListPriceMin() != null) {
            spec = spec.and(listPriceMin(criteria.getListPriceMin()));
        }
        if (criteria.getListPriceMax() != null) {
            spec = spec.and(listPriceMax(criteria.getListPriceMax()));
        }
        if (hasText(criteria.getCurrency())) {
            spec = spec.and(currencyContains(criteria.getCurrency().trim()));
        }
        if (criteria.getLeadTimeDaysMin() != null) {
            spec = spec.and(leadTimeDaysMin(criteria.getLeadTimeDaysMin()));
        }
        if (criteria.getLeadTimeDaysMax() != null) {
            spec = spec.and(leadTimeDaysMax(criteria.getLeadTimeDaysMax()));
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
