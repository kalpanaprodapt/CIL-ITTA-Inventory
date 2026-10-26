package com.uniti.cil.itta.inventory.ipam.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.DnsRecordSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing DnsRecord search. */
public final class DnsRecordSpecifications {

    private DnsRecordSpecifications() {
    }

    public static Specification<DnsRecord> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<DnsRecord> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<DnsRecord> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<DnsRecord> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> zoneNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("zoneName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> recordTypeIn(List<DnsRecordRecordType> values) {
        return (root, query, cb) -> root.get("recordType").in(values);
    }

    public static Specification<DnsRecord> recordValueContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("recordValue")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> ttlMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("ttl"), value);
    }

    public static Specification<DnsRecord> ttlMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("ttl"), value);
    }

    public static Specification<DnsRecord> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DnsRecord> criticalityIn(List<DnsRecordCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<DnsRecord> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<DnsRecord> fromCriteria(String tenantId, DnsRecordSearchCriteria criteria) {
        Specification<DnsRecord> spec = tenant(tenantId);
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
        if (hasText(criteria.getZoneName())) {
            spec = spec.and(zoneNameContains(criteria.getZoneName().trim()));
        }
        if (criteria.getRecordType() != null && !criteria.getRecordType().isEmpty()) {
            spec = spec.and(recordTypeIn(criteria.getRecordType()));
        }
        if (hasText(criteria.getRecordValue())) {
            spec = spec.and(recordValueContains(criteria.getRecordValue().trim()));
        }
        if (criteria.getTtlMin() != null) {
            spec = spec.and(ttlMin(criteria.getTtlMin()));
        }
        if (criteria.getTtlMax() != null) {
            spec = spec.and(ttlMax(criteria.getTtlMax()));
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
