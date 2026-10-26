package com.uniti.cil.itta.inventory.geography.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.CountrySearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Country search. */
public final class CountrySpecifications {

    private CountrySpecifications() {
    }

    public static Specification<Country> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Country> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Country> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Country> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> isoCodeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("isoCode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> isoNumericMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("isoNumeric"), value);
    }

    public static Specification<Country> isoNumericMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("isoNumeric"), value);
    }

    public static Specification<Country> dialCodeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("dialCode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> currencyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("currency")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> regulatoryBodyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("regulatoryBody")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> timezoneContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("timezone")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Country> criticalityIn(List<CountryCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Country> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Country> fromCriteria(String tenantId, CountrySearchCriteria criteria) {
        Specification<Country> spec = tenant(tenantId);
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
        if (hasText(criteria.getIsoCode())) {
            spec = spec.and(isoCodeContains(criteria.getIsoCode().trim()));
        }
        if (criteria.getIsoNumericMin() != null) {
            spec = spec.and(isoNumericMin(criteria.getIsoNumericMin()));
        }
        if (criteria.getIsoNumericMax() != null) {
            spec = spec.and(isoNumericMax(criteria.getIsoNumericMax()));
        }
        if (hasText(criteria.getDialCode())) {
            spec = spec.and(dialCodeContains(criteria.getDialCode().trim()));
        }
        if (hasText(criteria.getCurrency())) {
            spec = spec.and(currencyContains(criteria.getCurrency().trim()));
        }
        if (hasText(criteria.getRegulatoryBody())) {
            spec = spec.and(regulatoryBodyContains(criteria.getRegulatoryBody().trim()));
        }
        if (hasText(criteria.getTimezone())) {
            spec = spec.and(timezoneContains(criteria.getTimezone().trim()));
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
