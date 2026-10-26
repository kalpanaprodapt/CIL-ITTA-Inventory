"""Normalisation of inbound Mpls Lsp records coming from external systems."""
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
from itta_api.domains.mpls.mpls_lsp.schemas import *  # noqa: F401,F403


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

def normalize_lsp_name(value: object) -> str | None:
    """Normalize Lsp Name: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_head_end_id(value: object) -> UUID | None:
    """Normalize Head End Id to a UUID."""
    return to_uuid(value)

def normalize_tail_end_id(value: object) -> UUID | None:
    """Normalize Tail End Id to a UUID."""
    return to_uuid(value)

def normalize_bandwidth_mbps(value: object) -> int | None:
    """Normalize Bandwidth Mbps to an integer."""
    return to_int(value)

def normalize_setup_priority(value: object) -> int | None:
    """Normalize Setup Priority to an integer."""
    return to_int(value)

def normalize_path_type(value: object) -> MplsLspPathType | None:
    """Normalize Path Type to its enum member (case and separator insensitive)."""
    return to_enum(MplsLspPathType, value)

def normalize_source_system(value: object) -> str | None:
    """Normalize Source System: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_region(value: object) -> str | None:
    """Normalize Region: trim, collapse whitespace."""
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

def normalize_criticality(value: object) -> MplsLspCriticality | None:
    """Normalize Criticality to its enum member (case and separator insensitive)."""
    return to_enum(MplsLspCriticality, value)

def normalize_cost_center(value: object) -> str | None:
    """Normalize Cost Center: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "lspName": normalize_lsp_name,
    "headEndId": normalize_head_end_id,
    "tailEndId": normalize_tail_end_id,
    "bandwidthMbps": normalize_bandwidth_mbps,
    "setupPriority": normalize_setup_priority,
    "pathType": normalize_path_type,
    "sourceSystem": normalize_source_system,
    "region": normalize_region,
    "owner": normalize_owner,
    "criticality": normalize_criticality,
    "costCenter": normalize_cost_center,
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
