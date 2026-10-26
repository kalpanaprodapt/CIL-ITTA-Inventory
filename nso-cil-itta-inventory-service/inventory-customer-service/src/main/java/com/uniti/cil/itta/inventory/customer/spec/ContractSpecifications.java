package com.uniti.cil.itta.inventory.customer.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.ContractSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Contract search. */
public final class ContractSpecifications {

    private ContractSpecifications() {
    }

    public static Specification<Contract> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Contract> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Contract> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Contract> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> customerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), value);
    }

    public static Specification<Contract> contractNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("contractNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> startDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<Contract> startDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<Contract> endDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("endDate"), value);
    }

    public static Specification<Contract> endDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("endDate"), value);
    }

    public static Specification<Contract> termMonthsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("termMonths"), value);
    }

    public static Specification<Contract> termMonthsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("termMonths"), value);
    }

    public static Specification<Contract> autoRenewEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("autoRenew"), value);
    }

    public static Specification<Contract> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contract> criticalityIn(List<ContractCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Contract> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Contract> fromCriteria(String tenantId, ContractSearchCriteria criteria) {
        Specification<Contract> spec = tenant(tenantId);
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
        if (hasText(criteria.getContractNumber())) {
            spec = spec.and(contractNumberContains(criteria.getContractNumber().trim()));
        }
        if (criteria.getStartDateFrom() != null) {
            spec = spec.and(startDateFrom(criteria.getStartDateFrom()));
        }
        if (criteria.getStartDateTo() != null) {
            spec = spec.and(startDateTo(criteria.getStartDateTo()));
        }
        if (criteria.getEndDateFrom() != null) {
            spec = spec.and(endDateFrom(criteria.getEndDateFrom()));
        }
        if (criteria.getEndDateTo() != null) {
            spec = spec.and(endDateTo(criteria.getEndDateTo()));
        }
        if (criteria.getTermMonthsMin() != null) {
            spec = spec.and(termMonthsMin(criteria.getTermMonthsMin()));
        }
        if (criteria.getTermMonthsMax() != null) {
            spec = spec.and(termMonthsMax(criteria.getTermMonthsMax()));
        }
        if (criteria.getAutoRenew() != null) {
            spec = spec.and(autoRenewEquals(criteria.getAutoRenew()));
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
