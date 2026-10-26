package com.uniti.cil.itta.inventory.alarm.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.EventSourceSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing EventSource search. */
public final class EventSourceSpecifications {

    private EventSourceSpecifications() {
    }

    public static Specification<EventSource> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<EventSource> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<EventSource> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<EventSource> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> sourceNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> protocolIn(List<EventSourceProtocol> values) {
        return (root, query, cb) -> root.get("protocol").in(values);
    }

    public static Specification<EventSource> endpointContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("endpoint")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> heartbeatSecondsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("heartbeatSeconds"), value);
    }

    public static Specification<EventSource> heartbeatSecondsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("heartbeatSeconds"), value);
    }

    public static Specification<EventSource> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> lifecycleStageIn(List<EventSourceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<EventSource> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<EventSource> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<EventSource> fromCriteria(String tenantId, EventSourceSearchCriteria criteria) {
        Specification<EventSource> spec = tenant(tenantId);
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
        if (hasText(criteria.getSourceName())) {
            spec = spec.and(sourceNameContains(criteria.getSourceName().trim()));
        }
        if (criteria.getProtocol() != null && !criteria.getProtocol().isEmpty()) {
            spec = spec.and(protocolIn(criteria.getProtocol()));
        }
        if (hasText(criteria.getEndpoint())) {
            spec = spec.and(endpointContains(criteria.getEndpoint().trim()));
        }
        if (criteria.getHeartbeatSecondsMin() != null) {
            spec = spec.and(heartbeatSecondsMin(criteria.getHeartbeatSecondsMin()));
        }
        if (criteria.getHeartbeatSecondsMax() != null) {
            spec = spec.and(heartbeatSecondsMax(criteria.getHeartbeatSecondsMax()));
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
