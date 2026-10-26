package com.uniti.cil.itta.inventory.service.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ServiceEndpoint search. */
public final class ServiceEndpointSpecifications {

    private ServiceEndpointSpecifications() {
    }

    public static Specification<ServiceEndpoint> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ServiceEndpoint> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ServiceEndpoint> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ServiceEndpoint> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceEndpoint> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceEndpoint> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceEndpoint> serviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("serviceId"), value);
    }

    public static Specification<ServiceEndpoint> siteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("siteId"), value);
    }

    public static Specification<ServiceEndpoint> portIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("portId"), value);
    }

    public static Specification<ServiceEndpoint> endpointRoleIn(List<ServiceEndpointEndpointRole> values) {
        return (root, query, cb) -> root.get("endpointRole").in(values);
    }

    public static Specification<ServiceEndpoint> handoffContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("handoff")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceEndpoint> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ServiceEndpoint> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ServiceEndpoint> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ServiceEndpoint> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ServiceEndpoint> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceEndpoint> lifecycleStageIn(List<ServiceEndpointLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ServiceEndpoint> fromCriteria(String tenantId, ServiceEndpointSearchCriteria criteria) {
        Specification<ServiceEndpoint> spec = tenant(tenantId);
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
        if (criteria.getServiceId() != null) {
            spec = spec.and(serviceIdEquals(criteria.getServiceId()));
        }
        if (criteria.getSiteId() != null) {
            spec = spec.and(siteIdEquals(criteria.getSiteId()));
        }
        if (criteria.getPortId() != null) {
            spec = spec.and(portIdEquals(criteria.getPortId()));
        }
        if (criteria.getEndpointRole() != null && !criteria.getEndpointRole().isEmpty()) {
            spec = spec.and(endpointRoleIn(criteria.getEndpointRole()));
        }
        if (hasText(criteria.getHandoff())) {
            spec = spec.and(handoffContains(criteria.getHandoff().trim()));
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
        return spec;
    }
}
