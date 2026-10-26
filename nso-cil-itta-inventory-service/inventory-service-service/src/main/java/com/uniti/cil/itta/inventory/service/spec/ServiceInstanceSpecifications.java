package com.uniti.cil.itta.inventory.service.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ServiceInstance search. */
public final class ServiceInstanceSpecifications {

    private ServiceInstanceSpecifications() {
    }

    public static Specification<ServiceInstance> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ServiceInstance> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ServiceInstance> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ServiceInstance> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> catalogItemIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("catalogItemId"), value);
    }

    public static Specification<ServiceInstance> customerIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), value);
    }

    public static Specification<ServiceInstance> activatedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("activatedOn"), value);
    }

    public static Specification<ServiceInstance> activatedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("activatedOn"), value);
    }

    public static Specification<ServiceInstance> bandwidthMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<ServiceInstance> bandwidthMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("bandwidthMbps"), value);
    }

    public static Specification<ServiceInstance> serviceStateIn(List<ServiceInstanceServiceState> values) {
        return (root, query, cb) -> root.get("serviceState").in(values);
    }

    public static Specification<ServiceInstance> lifecycleStageIn(List<ServiceInstanceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<ServiceInstance> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ServiceInstance> criticalityIn(List<ServiceInstanceCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ServiceInstance> fromCriteria(String tenantId, ServiceInstanceSearchCriteria criteria) {
        Specification<ServiceInstance> spec = tenant(tenantId);
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
        if (criteria.getCatalogItemId() != null) {
            spec = spec.and(catalogItemIdEquals(criteria.getCatalogItemId()));
        }
        if (criteria.getCustomerId() != null) {
            spec = spec.and(customerIdEquals(criteria.getCustomerId()));
        }
        if (criteria.getActivatedOnFrom() != null) {
            spec = spec.and(activatedOnFrom(criteria.getActivatedOnFrom()));
        }
        if (criteria.getActivatedOnTo() != null) {
            spec = spec.and(activatedOnTo(criteria.getActivatedOnTo()));
        }
        if (criteria.getBandwidthMbpsMin() != null) {
            spec = spec.and(bandwidthMbpsMin(criteria.getBandwidthMbpsMin()));
        }
        if (criteria.getBandwidthMbpsMax() != null) {
            spec = spec.and(bandwidthMbpsMax(criteria.getBandwidthMbpsMax()));
        }
        if (criteria.getServiceState() != null && !criteria.getServiceState().isEmpty()) {
            spec = spec.and(serviceStateIn(criteria.getServiceState()));
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
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        return spec;
    }
}
