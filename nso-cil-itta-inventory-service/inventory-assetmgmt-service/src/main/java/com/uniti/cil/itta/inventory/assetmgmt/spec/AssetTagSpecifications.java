package com.uniti.cil.itta.inventory.assetmgmt.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetTagSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing AssetTag search. */
public final class AssetTagSpecifications {

    private AssetTagSpecifications() {
    }

    public static Specification<AssetTag> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<AssetTag> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<AssetTag> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<AssetTag> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> assetIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("assetId"), value);
    }

    public static Specification<AssetTag> tagValueContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("tagValue")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> tagTypeIn(List<AssetTagTagType> values) {
        return (root, query, cb) -> root.get("tagType").in(values);
    }

    public static Specification<AssetTag> printedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("printedOn"), value);
    }

    public static Specification<AssetTag> printedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("printedOn"), value);
    }

    public static Specification<AssetTag> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<AssetTag> criticalityIn(List<AssetTagCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<AssetTag> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<AssetTag> fromCriteria(String tenantId, AssetTagSearchCriteria criteria) {
        Specification<AssetTag> spec = tenant(tenantId);
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
        if (criteria.getAssetId() != null) {
            spec = spec.and(assetIdEquals(criteria.getAssetId()));
        }
        if (hasText(criteria.getTagValue())) {
            spec = spec.and(tagValueContains(criteria.getTagValue().trim()));
        }
        if (criteria.getTagType() != null && !criteria.getTagType().isEmpty()) {
            spec = spec.and(tagTypeIn(criteria.getTagType()));
        }
        if (criteria.getPrintedOnFrom() != null) {
            spec = spec.and(printedOnFrom(criteria.getPrintedOnFrom()));
        }
        if (criteria.getPrintedOnTo() != null) {
            spec = spec.and(printedOnTo(criteria.getPrintedOnTo()));
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
