"""Normalisation of inbound Radio Unit records coming from external systems."""
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
from itta_api.domains.radio.radio_unit.schemas import *  # noqa: F401,F403


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

def normalize_site_id(value: object) -> UUID | None:
    """Normalize Site Id to a UUID."""
    return to_uuid(value)

def normalize_unit_type(value: object) -> RadioUnitUnitType | None:
    """Normalize Unit Type to its enum member (case and separator insensitive)."""
    return to_enum(RadioUnitUnitType, value)

def normalize_band_mhz(value: object) -> int | None:
    """Normalize Band Mhz to an integer."""
    return to_int(value)

def normalize_tx_power_dbm(value: object) -> Decimal | None:
    """Normalize Tx Power Dbm to four decimal places."""
    return to_decimal(value, places=4)

def normalize_serial_number(value: object) -> str | None:
    """Normalize Serial Number: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_effective_from(value: object) -> date | None:
    """Normalize Effective From from ISO or day-first strings."""
    return to_date(value)

def normalize_effective_to(value: object) -> date | None:
    """Normalize Effective To from ISO or day-first strings."""
    return to_date(value)

def normalize_labels(value: object) -> str | None:
    """Normalize Labels: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_lifecycle_stage(value: object) -> RadioUnitLifecycleStage | None:
    """Normalize Lifecycle Stage to its enum member (case and separator insensitive)."""
    return to_enum(RadioUnitLifecycleStage, value)

def normalize_source_system(value: object) -> str | None:
    """Normalize Source System: trim, collapse whitespace."""
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
    "siteId": normalize_site_id,
    "unitType": normalize_unit_type,
    "bandMhz": normalize_band_mhz,
    "txPowerDbm": normalize_tx_power_dbm,
    "serialNumber": normalize_serial_number,
    "effectiveFrom": normalize_effective_from,
    "effectiveTo": normalize_effective_to,
    "labels": normalize_labels,
    "lifecycleStage": normalize_lifecycle_stage,
    "sourceSystem": normalize_source_system,
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
