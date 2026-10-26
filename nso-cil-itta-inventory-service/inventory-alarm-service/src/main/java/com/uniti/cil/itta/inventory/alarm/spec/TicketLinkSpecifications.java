package com.uniti.cil.itta.inventory.alarm.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.TicketLinkSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing TicketLink search. */
public final class TicketLinkSpecifications {

    private TicketLinkSpecifications() {
    }

    public static Specification<TicketLink> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TicketLink> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<TicketLink> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<TicketLink> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> alarmIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("alarmId"), value);
    }

    public static Specification<TicketLink> ticketSystemIn(List<TicketLinkTicketSystem> values) {
        return (root, query, cb) -> root.get("ticketSystem").in(values);
    }

    public static Specification<TicketLink> ticketNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ticketNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> linkedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("linkedAt"), value);
    }

    public static Specification<TicketLink> linkedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("linkedAt"), value);
    }

    public static Specification<TicketLink> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> criticalityIn(List<TicketLinkCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<TicketLink> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TicketLink> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TicketLink> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<TicketLink> fromCriteria(String tenantId, TicketLinkSearchCriteria criteria) {
        Specification<TicketLink> spec = tenant(tenantId);
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
        if (criteria.getAlarmId() != null) {
            spec = spec.and(alarmIdEquals(criteria.getAlarmId()));
        }
        if (criteria.getTicketSystem() != null && !criteria.getTicketSystem().isEmpty()) {
            spec = spec.and(ticketSystemIn(criteria.getTicketSystem()));
        }
        if (hasText(criteria.getTicketNumber())) {
            spec = spec.and(ticketNumberContains(criteria.getTicketNumber().trim()));
        }
        if (criteria.getLinkedAtFrom() != null) {
            spec = spec.and(linkedAtFrom(criteria.getLinkedAtFrom()));
        }
        if (criteria.getLinkedAtTo() != null) {
            spec = spec.and(linkedAtTo(criteria.getLinkedAtTo()));
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
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        return spec;
    }
}
