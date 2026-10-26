package com.uniti.cil.itta.inventory.software.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.SoftwareImageSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SoftwareImage search. */
public final class SoftwareImageSpecifications {

    private SoftwareImageSpecifications() {
    }

    public static Specification<SoftwareImage> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SoftwareImage> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SoftwareImage> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SoftwareImage> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<SoftwareImage> imageNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("imageName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> sizeMbMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("sizeMb"), value);
    }

    public static Specification<SoftwareImage> sizeMbMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("sizeMb"), value);
    }

    public static Specification<SoftwareImage> checksumContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("checksum")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> platformContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("platform")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> storagePathContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("storagePath")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> lifecycleStageIn(List<SoftwareImageLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<SoftwareImage> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareImage> criticalityIn(List<SoftwareImageCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SoftwareImage> fromCriteria(String tenantId, SoftwareImageSearchCriteria criteria) {
        Specification<SoftwareImage> spec = tenant(tenantId);
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
        if (criteria.getVendorId() != null) {
            spec = spec.and(vendorIdEquals(criteria.getVendorId()));
        }
        if (hasText(criteria.getImageName())) {
            spec = spec.and(imageNameContains(criteria.getImageName().trim()));
        }
        if (criteria.getSizeMbMin() != null) {
            spec = spec.and(sizeMbMin(criteria.getSizeMbMin()));
        }
        if (criteria.getSizeMbMax() != null) {
            spec = spec.and(sizeMbMax(criteria.getSizeMbMax()));
        }
        if (hasText(criteria.getChecksum())) {
            spec = spec.and(checksumContains(criteria.getChecksum().trim()));
        }
        if (hasText(criteria.getPlatform())) {
            spec = spec.and(platformContains(criteria.getPlatform().trim()));
        }
        if (hasText(criteria.getStoragePath())) {
            spec = spec.and(storagePathContains(criteria.getStoragePath().trim()));
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
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        return spec;
    }
}
