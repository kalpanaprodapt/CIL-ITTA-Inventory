package com.uniti.cil.itta.inventory.service.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceOrderSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ServiceOrder search. */
public final class ServiceOrderSpecifications {

    private ServiceOrderSpecifications() {
    }

    public static Specification<ServiceOrder> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ServiceOrder> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ServiceOrder> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ServiceOrder> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceOrder> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceOrder> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceOrder> customerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), value);
    }

    public static Specification<ServiceOrder> orderTypeIn(List<ServiceOrderOrderType> values) {
        return (root, query, cb) -> root.get("orderType").in(values);
    }

    public static Specification<ServiceOrder> requestedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("requestedOn"), value);
    }

    public static Specification<ServiceOrder> requestedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("requestedOn"), value);
    }

    public static Specification<ServiceOrder> targetDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("targetDate"), value);
    }

    public static Specification<ServiceOrder> targetDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("targetDate"), value);
    }

    public static Specification<ServiceOrder> priorityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("priority"), value);
    }

    public static Specification<ServiceOrder> priorityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("priority"), value);
    }

    public static Specification<ServiceOrder> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceOrder> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceOrder> criticalityIn(List<ServiceOrderCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ServiceOrder> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ServiceOrder> fromCriteria(String tenantId, ServiceOrderSearchCriteria criteria) {
        Specification<ServiceOrder> spec = tenant(tenantId);
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
        if (criteria.getCustomerId() != null) {
            spec = spec.and(customerIdEquals(criteria.getCustomerId()));
        }
        if (criteria.getOrderType() != null && !criteria.getOrderType().isEmpty()) {
            spec = spec.and(orderTypeIn(criteria.getOrderType()));
        }
        if (criteria.getRequestedOnFrom() != null) {
            spec = spec.and(requestedOnFrom(criteria.getRequestedOnFrom()));
        }
        if (criteria.getRequestedOnTo() != null) {
            spec = spec.and(requestedOnTo(criteria.getRequestedOnTo()));
        }
        if (criteria.getTargetDateFrom() != null) {
            spec = spec.and(targetDateFrom(criteria.getTargetDateFrom()));
        }
        if (criteria.getTargetDateTo() != null) {
            spec = spec.and(targetDateTo(criteria.getTargetDateTo()));
        }
        if (criteria.getPriorityMin() != null) {
            spec = spec.and(priorityMin(criteria.getPriorityMin()));
        }
        if (criteria.getPriorityMax() != null) {
            spec = spec.and(priorityMax(criteria.getPriorityMax()));
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
