package com.uniti.cil.itta.inventory.switching.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.BgpSessionSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing BgpSession search. */
public final class BgpSessionSpecifications {

    private BgpSessionSpecifications() {
    }

    public static Specification<BgpSession> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<BgpSession> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<BgpSession> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<BgpSession> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> localDeviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("localDeviceId"), value);
    }

    public static Specification<BgpSession> peerAddressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("peerAddress")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> localAsnMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("localAsn"), value);
    }

    public static Specification<BgpSession> localAsnMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("localAsn"), value);
    }

    public static Specification<BgpSession> remoteAsnMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("remoteAsn"), value);
    }

    public static Specification<BgpSession> remoteAsnMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("remoteAsn"), value);
    }

    public static Specification<BgpSession> sessionStateIn(List<BgpSessionSessionState> values) {
        return (root, query, cb) -> root.get("sessionState").in(values);
    }

    public static Specification<BgpSession> prefixLimitMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("prefixLimit"), value);
    }

    public static Specification<BgpSession> prefixLimitMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("prefixLimit"), value);
    }

    public static Specification<BgpSession> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BgpSession> criticalityIn(List<BgpSessionCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<BgpSession> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<BgpSession> fromCriteria(String tenantId, BgpSessionSearchCriteria criteria) {
        Specification<BgpSession> spec = tenant(tenantId);
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
        if (criteria.getLocalDeviceId() != null) {
            spec = spec.and(localDeviceIdEquals(criteria.getLocalDeviceId()));
        }
        if (hasText(criteria.getPeerAddress())) {
            spec = spec.and(peerAddressContains(criteria.getPeerAddress().trim()));
        }
        if (criteria.getLocalAsnMin() != null) {
            spec = spec.and(localAsnMin(criteria.getLocalAsnMin()));
        }
        if (criteria.getLocalAsnMax() != null) {
            spec = spec.and(localAsnMax(criteria.getLocalAsnMax()));
        }
        if (criteria.getRemoteAsnMin() != null) {
            spec = spec.and(remoteAsnMin(criteria.getRemoteAsnMin()));
        }
        if (criteria.getRemoteAsnMax() != null) {
            spec = spec.and(remoteAsnMax(criteria.getRemoteAsnMax()));
        }
        if (criteria.getSessionState() != null && !criteria.getSessionState().isEmpty()) {
            spec = spec.and(sessionStateIn(criteria.getSessionState()));
        }
        if (criteria.getPrefixLimitMin() != null) {
            spec = spec.and(prefixLimitMin(criteria.getPrefixLimitMin()));
        }
        if (criteria.getPrefixLimitMax() != null) {
            spec = spec.and(prefixLimitMax(criteria.getPrefixLimitMax()));
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
