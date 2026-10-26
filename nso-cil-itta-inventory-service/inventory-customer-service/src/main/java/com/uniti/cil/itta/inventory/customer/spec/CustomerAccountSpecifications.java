package com.uniti.cil.itta.inventory.customer.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.CustomerAccountSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CustomerAccount search. */
public final class CustomerAccountSpecifications {

    private CustomerAccountSpecifications() {
    }

    public static Specification<CustomerAccount> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CustomerAccount> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CustomerAccount> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CustomerAccount> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> customerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), value);
    }

    public static Specification<CustomerAccount> accountNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("accountNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> billingCycleIn(List<CustomerAccountBillingCycle> values) {
        return (root, query, cb) -> root.get("billingCycle").in(values);
    }

    public static Specification<CustomerAccount> currencyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("currency")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> creditLimitMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("creditLimit"), value);
    }

    public static Specification<CustomerAccount> creditLimitMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("creditLimit"), value);
    }

    public static Specification<CustomerAccount> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CustomerAccount> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CustomerAccount> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CustomerAccount> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CustomerAccount> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CustomerAccount> lifecycleStageIn(List<CustomerAccountLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<CustomerAccount> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CustomerAccount> fromCriteria(String tenantId, CustomerAccountSearchCriteria criteria) {
        Specification<CustomerAccount> spec = tenant(tenantId);
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
        if (hasText(criteria.getAccountNumber())) {
            spec = spec.and(accountNumberContains(criteria.getAccountNumber().trim()));
        }
        if (criteria.getBillingCycle() != null && !criteria.getBillingCycle().isEmpty()) {
            spec = spec.and(billingCycleIn(criteria.getBillingCycle()));
        }
        if (hasText(criteria.getCurrency())) {
            spec = spec.and(currencyContains(criteria.getCurrency().trim()));
        }
        if (criteria.getCreditLimitMin() != null) {
            spec = spec.and(creditLimitMin(criteria.getCreditLimitMin()));
        }
        if (criteria.getCreditLimitMax() != null) {
            spec = spec.and(creditLimitMax(criteria.getCreditLimitMax()));
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
