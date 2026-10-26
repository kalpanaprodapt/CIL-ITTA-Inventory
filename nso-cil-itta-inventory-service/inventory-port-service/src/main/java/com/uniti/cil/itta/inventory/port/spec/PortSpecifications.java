package com.uniti.cil.itta.inventory.port.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.PortSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Port search. */
public final class PortSpecifications {

    private PortSpecifications() {
    }

    public static Specification<Port> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Port> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Port> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Port> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> deviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("deviceId"), value);
    }

    public static Specification<Port> cardIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("cardId"), value);
    }

    public static Specification<Port> portNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("portNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> speedMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("speedMbps"), value);
    }

    public static Specification<Port> speedMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("speedMbps"), value);
    }

    public static Specification<Port> mediaTypeIn(List<PortMediaType> values) {
        return (root, query, cb) -> root.get("mediaType").in(values);
    }

    public static Specification<Port> adminStateIn(List<PortAdminState> values) {
        return (root, query, cb) -> root.get("adminState").in(values);
    }

    public static Specification<Port> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Port> criticalityIn(List<PortCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Port> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Port> fromCriteria(String tenantId, PortSearchCriteria criteria) {
        Specification<Port> spec = tenant(tenantId);
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
        if (criteria.getCardId() != null) {
            spec = spec.and(cardIdEquals(criteria.getCardId()));
        }
        if (hasText(criteria.getPortNumber())) {
            spec = spec.and(portNumberContains(criteria.getPortNumber().trim()));
        }
        if (criteria.getSpeedMbpsMin() != null) {
            spec = spec.and(speedMbpsMin(criteria.getSpeedMbpsMin()));
        }
        if (criteria.getSpeedMbpsMax() != null) {
            spec = spec.and(speedMbpsMax(criteria.getSpeedMbpsMax()));
        }
        if (criteria.getMediaType() != null && !criteria.getMediaType().isEmpty()) {
            spec = spec.and(mediaTypeIn(criteria.getMediaType()));
        }
        if (criteria.getAdminState() != null && !criteria.getAdminState().isEmpty()) {
            spec = spec.and(adminStateIn(criteria.getAdminState()));
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
