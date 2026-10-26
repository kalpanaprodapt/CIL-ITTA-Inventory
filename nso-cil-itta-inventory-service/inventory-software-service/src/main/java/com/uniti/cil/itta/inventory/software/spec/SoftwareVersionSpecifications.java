package com.uniti.cil.itta.inventory.software.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.SoftwareVersionSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SoftwareVersion search. */
public final class SoftwareVersionSpecifications {

    private SoftwareVersionSpecifications() {
    }

    public static Specification<SoftwareVersion> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SoftwareVersion> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SoftwareVersion> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SoftwareVersion> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> imageIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("imageId"), value);
    }

    public static Specification<SoftwareVersion> versionStringContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("versionString")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> releaseDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("releaseDate"), value);
    }

    public static Specification<SoftwareVersion> releaseDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("releaseDate"), value);
    }

    public static Specification<SoftwareVersion> endOfSupportFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("endOfSupport"), value);
    }

    public static Specification<SoftwareVersion> endOfSupportTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("endOfSupport"), value);
    }

    public static Specification<SoftwareVersion> releaseTrainIn(List<SoftwareVersionReleaseTrain> values) {
        return (root, query, cb) -> root.get("releaseTrain").in(values);
    }

    public static Specification<SoftwareVersion> cveCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("cveCount"), value);
    }

    public static Specification<SoftwareVersion> cveCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("cveCount"), value);
    }

    public static Specification<SoftwareVersion> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SoftwareVersion> criticalityIn(List<SoftwareVersionCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<SoftwareVersion> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SoftwareVersion> fromCriteria(String tenantId, SoftwareVersionSearchCriteria criteria) {
        Specification<SoftwareVersion> spec = tenant(tenantId);
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
        if (criteria.getImageId() != null) {
            spec = spec.and(imageIdEquals(criteria.getImageId()));
        }
        if (hasText(criteria.getVersionString())) {
            spec = spec.and(versionStringContains(criteria.getVersionString().trim()));
        }
        if (criteria.getReleaseDateFrom() != null) {
            spec = spec.and(releaseDateFrom(criteria.getReleaseDateFrom()));
        }
        if (criteria.getReleaseDateTo() != null) {
            spec = spec.and(releaseDateTo(criteria.getReleaseDateTo()));
        }
        if (criteria.getEndOfSupportFrom() != null) {
            spec = spec.and(endOfSupportFrom(criteria.getEndOfSupportFrom()));
        }
        if (criteria.getEndOfSupportTo() != null) {
            spec = spec.and(endOfSupportTo(criteria.getEndOfSupportTo()));
        }
        if (criteria.getReleaseTrain() != null && !criteria.getReleaseTrain().isEmpty()) {
            spec = spec.and(releaseTrainIn(criteria.getReleaseTrain()));
        }
        if (criteria.getCveCountMin() != null) {
            spec = spec.and(cveCountMin(criteria.getCveCountMin()));
        }
        if (criteria.getCveCountMax() != null) {
            spec = spec.and(cveCountMax(criteria.getCveCountMax()));
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
