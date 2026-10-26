package com.uniti.cil.itta.inventory.change.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.WorkOrderSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing WorkOrder search. */
public final class WorkOrderSpecifications {

    private WorkOrderSpecifications() {
    }

    public static Specification<WorkOrder> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<WorkOrder> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<WorkOrder> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<WorkOrder> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> workOrderNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("workOrderNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> workTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("workType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> assignedTechnicianIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("assignedTechnicianId"), value);
    }

    public static Specification<WorkOrder> dueDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("dueDate"), value);
    }

    public static Specification<WorkOrder> dueDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("dueDate"), value);
    }

    public static Specification<WorkOrder> workStateIn(List<WorkOrderWorkState> values) {
        return (root, query, cb) -> root.get("workState").in(values);
    }

    public static Specification<WorkOrder> lifecycleStageIn(List<WorkOrderLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<WorkOrder> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WorkOrder> criticalityIn(List<WorkOrderCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<WorkOrder> fromCriteria(String tenantId, WorkOrderSearchCriteria criteria) {
        Specification<WorkOrder> spec = tenant(tenantId);
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
        if (hasText(criteria.getWorkOrderNumber())) {
            spec = spec.and(workOrderNumberContains(criteria.getWorkOrderNumber().trim()));
        }
        if (hasText(criteria.getWorkType())) {
            spec = spec.and(workTypeContains(criteria.getWorkType().trim()));
        }
        if (criteria.getAssignedTechnicianId() != null) {
            spec = spec.and(assignedTechnicianIdEquals(criteria.getAssignedTechnicianId()));
        }
        if (criteria.getDueDateFrom() != null) {
            spec = spec.and(dueDateFrom(criteria.getDueDateFrom()));
        }
        if (criteria.getDueDateTo() != null) {
            spec = spec.and(dueDateTo(criteria.getDueDateTo()));
        }
        if (criteria.getWorkState() != null && !criteria.getWorkState().isEmpty()) {
            spec = spec.and(workStateIn(criteria.getWorkState()));
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
