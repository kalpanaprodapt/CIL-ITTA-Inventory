package com.uniti.cil.itta.inventory.customer.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.CustomerContactSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CustomerContact search. */
public final class CustomerContactSpecifications {

    private CustomerContactSpecifications() {
    }

    public static Specification<CustomerContact> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CustomerContact> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CustomerContact> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CustomerContact> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> customerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), value);
    }

    public static Specification<CustomerContact> fullNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("fullName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> emailContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> phoneContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("phone")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> contactRoleIn(List<CustomerContactContactRole> values) {
        return (root, query, cb) -> root.get("contactRole").in(values);
    }

    public static Specification<CustomerContact> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> lifecycleStageIn(List<CustomerContactLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<CustomerContact> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerContact> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CustomerContact> fromCriteria(String tenantId, CustomerContactSearchCriteria criteria) {
        Specification<CustomerContact> spec = tenant(tenantId);
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
        if (hasText(criteria.getFullName())) {
            spec = spec.and(fullNameContains(criteria.getFullName().trim()));
        }
        if (hasText(criteria.getEmail())) {
            spec = spec.and(emailContains(criteria.getEmail().trim()));
        }
        if (hasText(criteria.getPhone())) {
            spec = spec.and(phoneContains(criteria.getPhone().trim()));
        }
        if (criteria.getContactRole() != null && !criteria.getContactRole().isEmpty()) {
            spec = spec.and(contactRoleIn(criteria.getContactRole()));
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
