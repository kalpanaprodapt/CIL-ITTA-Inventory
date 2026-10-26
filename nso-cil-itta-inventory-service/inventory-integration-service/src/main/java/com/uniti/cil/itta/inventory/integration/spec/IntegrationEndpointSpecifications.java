package com.uniti.cil.itta.inventory.integration.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.IntegrationEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing IntegrationEndpoint search. */
public final class IntegrationEndpointSpecifications {

    private IntegrationEndpointSpecifications() {
    }

    public static Specification<IntegrationEndpoint> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<IntegrationEndpoint> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<IntegrationEndpoint> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<IntegrationEndpoint> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> endpointNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("endpointName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> targetSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("targetSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> baseUrlContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("baseUrl")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> authTypeIn(List<IntegrationEndpointAuthType> values) {
        return (root, query, cb) -> root.get("authType").in(values);
    }

    public static Specification<IntegrationEndpoint> timeoutMsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("timeoutMs"), value);
    }

    public static Specification<IntegrationEndpoint> timeoutMsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("timeoutMs"), value);
    }

    public static Specification<IntegrationEndpoint> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IntegrationEndpoint> criticalityIn(List<IntegrationEndpointCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<IntegrationEndpoint> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<IntegrationEndpoint> fromCriteria(String tenantId, IntegrationEndpointSearchCriteria criteria) {
        Specification<IntegrationEndpoint> spec = tenant(tenantId);
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
        if (hasText(criteria.getEndpointName())) {
            spec = spec.and(endpointNameContains(criteria.getEndpointName().trim()));
        }
        if (hasText(criteria.getTargetSystem())) {
            spec = spec.and(targetSystemContains(criteria.getTargetSystem().trim()));
        }
        if (hasText(criteria.getBaseUrl())) {
            spec = spec.and(baseUrlContains(criteria.getBaseUrl().trim()));
        }
        if (criteria.getAuthType() != null && !criteria.getAuthType().isEmpty()) {
            spec = spec.and(authTypeIn(criteria.getAuthType()));
        }
        if (criteria.getTimeoutMsMin() != null) {
            spec = spec.and(timeoutMsMin(criteria.getTimeoutMsMin()));
        }
        if (criteria.getTimeoutMsMax() != null) {
            spec = spec.and(timeoutMsMax(criteria.getTimeoutMsMax()));
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
