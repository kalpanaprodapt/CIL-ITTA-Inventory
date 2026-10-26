"""Normalisation of inbound Hardware Model records coming from external systems."""
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
from itta_api.domains.vendor.hardware_model.schemas import *  # noqa: F401,F403


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

def normalize_vendor_id(value: object) -> UUID | None:
    """Normalize Vendor Id to a UUID."""
    return to_uuid(value)

def normalize_model_number(value: object) -> str | None:
    """Normalize Model Number: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_form_factor(value: object) -> str | None:
    """Normalize Form Factor: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_power_watts(value: object) -> int | None:
    """Normalize Power Watts to an integer."""
    return to_int(value)

def normalize_eol_date(value: object) -> date | None:
    """Normalize Eol Date from ISO or day-first strings."""
    return to_date(value)

def normalize_eos_date(value: object) -> date | None:
    """Normalize Eos Date from ISO or day-first strings."""
    return to_date(value)

def normalize_labels(value: object) -> str | None:
    """Normalize Labels: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_lifecycle_stage(value: object) -> HardwareModelLifecycleStage | None:
    """Normalize Lifecycle Stage to its enum member (case and separator insensitive)."""
    return to_enum(HardwareModelLifecycleStage, value)

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

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "vendorId": normalize_vendor_id,
    "modelNumber": normalize_model_number,
    "formFactor": normalize_form_factor,
    "powerWatts": normalize_power_watts,
    "eolDate": normalize_eol_date,
    "eosDate": normalize_eos_date,
    "labels": normalize_labels,
    "lifecycleStage": normalize_lifecycle_stage,
    "sourceSystem": normalize_source_system,
    "region": normalize_region,
    "owner": normalize_owner,
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
