package com.uniti.cil.itta.inventory.assetmgmt.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Asset search. */
public final class AssetSpecifications {

    private AssetSpecifications() {
    }

    public static Specification<Asset> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Asset> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Asset> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Asset> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> assetTagContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("assetTag")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> assetClassContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("assetClass")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> acquiredOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("acquiredOn"), value);
    }

    public static Specification<Asset> acquiredOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("acquiredOn"), value);
    }

    public static Specification<Asset> purchasePriceMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("purchasePrice"), value);
    }

    public static Specification<Asset> purchasePriceMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("purchasePrice"), value);
    }

    public static Specification<Asset> currentValueMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("currentValue"), value);
    }

    public static Specification<Asset> currentValueMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("currentValue"), value);
    }

    public static Specification<Asset> custodianContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("custodian")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> lifecycleStageIn(List<AssetLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Asset> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Asset> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Asset> fromCriteria(String tenantId, AssetSearchCriteria criteria) {
        Specification<Asset> spec = tenant(tenantId);
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
        if (hasText(criteria.getAssetTag())) {
            spec = spec.and(assetTagContains(criteria.getAssetTag().trim()));
        }
        if (hasText(criteria.getAssetClass())) {
            spec = spec.and(assetClassContains(criteria.getAssetClass().trim()));
        }
        if (criteria.getAcquiredOnFrom() != null) {
            spec = spec.and(acquiredOnFrom(criteria.getAcquiredOnFrom()));
        }
        if (criteria.getAcquiredOnTo() != null) {
            spec = spec.and(acquiredOnTo(criteria.getAcquiredOnTo()));
        }
        if (criteria.getPurchasePriceMin() != null) {
            spec = spec.and(purchasePriceMin(criteria.getPurchasePriceMin()));
        }
        if (criteria.getPurchasePriceMax() != null) {
            spec = spec.and(purchasePriceMax(criteria.getPurchasePriceMax()));
        }
        if (criteria.getCurrentValueMin() != null) {
            spec = spec.and(currentValueMin(criteria.getCurrentValueMin()));
        }
        if (criteria.getCurrentValueMax() != null) {
            spec = spec.and(currentValueMax(criteria.getCurrentValueMax()));
        }
        if (hasText(criteria.getCustodian())) {
            spec = spec.and(custodianContains(criteria.getCustodian().trim()));
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
