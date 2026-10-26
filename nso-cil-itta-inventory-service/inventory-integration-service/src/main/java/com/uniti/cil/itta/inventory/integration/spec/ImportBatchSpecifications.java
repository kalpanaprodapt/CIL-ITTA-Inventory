package com.uniti.cil.itta.inventory.integration.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.ImportBatchSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ImportBatch search. */
public final class ImportBatchSpecifications {

    private ImportBatchSpecifications() {
    }

    public static Specification<ImportBatch> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ImportBatch> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ImportBatch> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ImportBatch> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> fileNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("fileName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> entityTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("entityType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> totalRowsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("totalRows"), value);
    }

    public static Specification<ImportBatch> totalRowsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("totalRows"), value);
    }

    public static Specification<ImportBatch> processedRowsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("processedRows"), value);
    }

    public static Specification<ImportBatch> processedRowsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("processedRows"), value);
    }

    public static Specification<ImportBatch> failedRowsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("failedRows"), value);
    }

    public static Specification<ImportBatch> failedRowsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("failedRows"), value);
    }

    public static Specification<ImportBatch> batchStateIn(List<ImportBatchBatchState> values) {
        return (root, query, cb) -> root.get("batchState").in(values);
    }

    public static Specification<ImportBatch> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ImportBatch> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ImportBatch> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ImportBatch> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ImportBatch> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ImportBatch> lifecycleStageIn(List<ImportBatchLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<ImportBatch> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ImportBatch> fromCriteria(String tenantId, ImportBatchSearchCriteria criteria) {
        Specification<ImportBatch> spec = tenant(tenantId);
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
        if (hasText(criteria.getFileName())) {
            spec = spec.and(fileNameContains(criteria.getFileName().trim()));
        }
        if (hasText(criteria.getEntityType())) {
            spec = spec.and(entityTypeContains(criteria.getEntityType().trim()));
        }
        if (criteria.getTotalRowsMin() != null) {
            spec = spec.and(totalRowsMin(criteria.getTotalRowsMin()));
        }
        if (criteria.getTotalRowsMax() != null) {
            spec = spec.and(totalRowsMax(criteria.getTotalRowsMax()));
        }
        if (criteria.getProcessedRowsMin() != null) {
            spec = spec.and(processedRowsMin(criteria.getProcessedRowsMin()));
        }
        if (criteria.getProcessedRowsMax() != null) {
            spec = spec.and(processedRowsMax(criteria.getProcessedRowsMax()));
        }
        if (criteria.getFailedRowsMin() != null) {
            spec = spec.and(failedRowsMin(criteria.getFailedRowsMin()));
        }
        if (criteria.getFailedRowsMax() != null) {
            spec = spec.and(failedRowsMax(criteria.getFailedRowsMax()));
        }
        if (criteria.getBatchState() != null && !criteria.getBatchState().isEmpty()) {
            spec = spec.and(batchStateIn(criteria.getBatchState()));
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
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        return spec;
    }
}
