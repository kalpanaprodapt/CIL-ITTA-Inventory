"""Normalisation of inbound Sla Profile records coming from external systems."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_core.normalize import (
    collapse_whitespace,
    to_bool,
    to_date,
    to_datetime,
    to_decimal,
    to_enum,
    to_int,
    to_uuid,
)
from itta_api.domains.customer.sla_profile.schemas import *  # noqa: F401,F403


def normalize_code(value: object) -> str | None:
    """Normalize Code: trim, collapse whitespace, upper-case."""
    if value is None:
        return None
    text = collapse_whitespace(str(value)).upper() if str(value).strip() else None
    return text or None

def normalize_name(value: object) -> str | None:
    """Normalize Name: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_description(value: object) -> str | None:
    """Normalize Description: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_status(value: object) -> LifecycleStatus | None:
    """Normalize Status to its enum member (case and separator insensitive)."""
    return to_enum(LifecycleStatus, value)

def normalize_external_ref(value: object) -> str | None:
    """Normalize External Ref: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_profile_name(value: object) -> str | None:
    """Normalize Profile Name: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_availability_pct(value: object) -> Decimal | None:
    """Normalize Availability Pct to four decimal places."""
    return to_decimal(value, places=4)

def normalize_mttr_minutes(value: object) -> int | None:
    """Normalize Mttr Minutes to an integer."""
    return to_int(value)

def normalize_latency_ms(value: object) -> Decimal | None:
    """Normalize Latency Ms to four decimal places."""
    return to_decimal(value, places=4)

def normalize_jitter_ms(value: object) -> Decimal | None:
    """Normalize Jitter Ms to four decimal places."""
    return to_decimal(value, places=4)

def normalize_credit_policy(value: object) -> str | None:
    """Normalize Credit Policy: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_owner(value: object) -> str | None:
    """Normalize Owner: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_criticality(value: object) -> SlaProfileCriticality | None:
    """Normalize Criticality to its enum member (case and separator insensitive)."""
    return to_enum(SlaProfileCriticality, value)

def normalize_cost_center(value: object) -> str | None:
    """Normalize Cost Center: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_notes(value: object) -> str | None:
    """Normalize Notes: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_effective_from(value: object) -> date | None:
    """Normalize Effective From from ISO or day-first strings."""
    return to_date(value)

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "profileName": normalize_profile_name,
    "availabilityPct": normalize_availability_pct,
    "mttrMinutes": normalize_mttr_minutes,
    "latencyMs": normalize_latency_ms,
    "jitterMs": normalize_jitter_ms,
    "creditPolicy": normalize_credit_policy,
    "owner": normalize_owner,
    "criticality": normalize_criticality,
    "costCenter": normalize_cost_center,
    "notes": normalize_notes,
    "effectiveFrom": normalize_effective_from,
}


def normalize_record(raw: dict[str, object]) -> dict[str, object]:
    """Normalise every known attribute of a raw record; unknown keys are dropped."""
    from itta_core.normalize import camelize_keys

    keyed = camelize_keys(raw)
    out: dict[str, object] = {}
    for name, fn in NORMALIZERS.items():
        if name in keyed:
            out[name] = fn(keyed[name])
    return out
