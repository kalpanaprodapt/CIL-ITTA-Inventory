package com.uniti.cil.itta.inventory.compliance.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.PolicyDocumentSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PolicyDocument search. */
public final class PolicyDocumentSpecifications {

    private PolicyDocumentSpecifications() {
    }

    public static Specification<PolicyDocument> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PolicyDocument> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PolicyDocument> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PolicyDocument> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> titleContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> docVersionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("docVersion")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> effectiveDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveDate"), value);
    }

    public static Specification<PolicyDocument> effectiveDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveDate"), value);
    }

    public static Specification<PolicyDocument> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> documentUrlContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("documentUrl")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> lifecycleStageIn(List<PolicyDocumentLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<PolicyDocument> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PolicyDocument> criticalityIn(List<PolicyDocumentCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<PolicyDocument> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PolicyDocument> fromCriteria(String tenantId, PolicyDocumentSearchCriteria criteria) {
        Specification<PolicyDocument> spec = tenant(tenantId);
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
        if (hasText(criteria.getTitle())) {
            spec = spec.and(titleContains(criteria.getTitle().trim()));
        }
        if (hasText(criteria.getDocVersion())) {
            spec = spec.and(docVersionContains(criteria.getDocVersion().trim()));
        }
        if (criteria.getEffectiveDateFrom() != null) {
            spec = spec.and(effectiveDateFrom(criteria.getEffectiveDateFrom()));
        }
        if (criteria.getEffectiveDateTo() != null) {
            spec = spec.and(effectiveDateTo(criteria.getEffectiveDateTo()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        if (hasText(criteria.getDocumentUrl())) {
            spec = spec.and(documentUrlContains(criteria.getDocumentUrl().trim()));
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
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
