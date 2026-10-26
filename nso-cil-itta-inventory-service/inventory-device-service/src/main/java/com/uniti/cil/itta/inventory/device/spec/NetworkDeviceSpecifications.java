package com.uniti.cil.itta.inventory.device.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.NetworkDeviceSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing NetworkDevice search. */
public final class NetworkDeviceSpecifications {

    private NetworkDeviceSpecifications() {
    }

    public static Specification<NetworkDevice> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<NetworkDevice> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<NetworkDevice> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<NetworkDevice> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> hostnameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("hostname")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> deviceRoleIn(List<NetworkDeviceDeviceRole> values) {
        return (root, query, cb) -> root.get("deviceRole").in(values);
    }

    public static Specification<NetworkDevice> mgmtIpContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("mgmtIp")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> modelIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("modelId"), value);
    }

    public static Specification<NetworkDevice> rackUnitIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("rackUnitId"), value);
    }

    public static Specification<NetworkDevice> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<NetworkDevice> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<NetworkDevice> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> lifecycleStageIn(List<NetworkDeviceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<NetworkDevice> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<NetworkDevice> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<NetworkDevice> fromCriteria(String tenantId, NetworkDeviceSearchCriteria criteria) {
        Specification<NetworkDevice> spec = tenant(tenantId);
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
        if (hasText(criteria.getHostname())) {
            spec = spec.and(hostnameContains(criteria.getHostname().trim()));
        }
        if (criteria.getDeviceRole() != null && !criteria.getDeviceRole().isEmpty()) {
            spec = spec.and(deviceRoleIn(criteria.getDeviceRole()));
        }
        if (hasText(criteria.getMgmtIp())) {
            spec = spec.and(mgmtIpContains(criteria.getMgmtIp().trim()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
        }
        if (criteria.getModelId() != null) {
            spec = spec.and(modelIdEquals(criteria.getModelId()));
        }
        if (criteria.getRackUnitId() != null) {
            spec = spec.and(rackUnitIdEquals(criteria.getRackUnitId()));
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
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        return spec;
    }
}
