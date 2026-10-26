package com.uniti.cil.itta.inventory.mpls.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.TunnelEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing TunnelEndpoint search. */
public final class TunnelEndpointSpecifications {

    private TunnelEndpointSpecifications() {
    }

    public static Specification<TunnelEndpoint> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TunnelEndpoint> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<TunnelEndpoint> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<TunnelEndpoint> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> deviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("deviceId"), value);
    }

    public static Specification<TunnelEndpoint> tunnelNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("tunnelName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> tunnelTypeIn(List<TunnelEndpointTunnelType> values) {
        return (root, query, cb) -> root.get("tunnelType").in(values);
    }

    public static Specification<TunnelEndpoint> sourceIpContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceIp")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> destinationIpContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("destinationIp")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TunnelEndpoint> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TunnelEndpoint> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TunnelEndpoint> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TunnelEndpoint> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TunnelEndpoint> lifecycleStageIn(List<TunnelEndpointLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<TunnelEndpoint> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<TunnelEndpoint> fromCriteria(String tenantId, TunnelEndpointSearchCriteria criteria) {
        Specification<TunnelEndpoint> spec = tenant(tenantId);
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
        if (criteria.getDeviceId() != null) {
            spec = spec.and(deviceIdEquals(criteria.getDeviceId()));
        }
        if (hasText(criteria.getTunnelName())) {
            spec = spec.and(tunnelNameContains(criteria.getTunnelName().trim()));
        }
        if (criteria.getTunnelType() != null && !criteria.getTunnelType().isEmpty()) {
            spec = spec.and(tunnelTypeIn(criteria.getTunnelType()));
        }
        if (hasText(criteria.getSourceIp())) {
            spec = spec.and(sourceIpContains(criteria.getSourceIp().trim()));
        }
        if (hasText(criteria.getDestinationIp())) {
            spec = spec.and(destinationIpContains(criteria.getDestinationIp().trim()));
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
