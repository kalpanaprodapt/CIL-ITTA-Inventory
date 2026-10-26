"""Normalisation of inbound Amplifier records coming from external systems."""
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
from itta_api.domains.optical.amplifier.schemas import *  # noqa: F401,F403


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

def normalize_system_id(value: object) -> UUID | None:
    """Normalize System Id to a UUID."""
    return to_uuid(value)

def normalize_amp_type(value: object) -> AmplifierAmpType | None:
    """Normalize Amp Type to its enum member (case and separator insensitive)."""
    return to_enum(AmplifierAmpType, value)

def normalize_gain_db(value: object) -> Decimal | None:
    """Normalize Gain Db to four decimal places."""
    return to_decimal(value, places=4)

def normalize_output_power_dbm(value: object) -> Decimal | None:
    """Normalize Output Power Dbm to four decimal places."""
    return to_decimal(value, places=4)

def normalize_noise_figure_db(value: object) -> Decimal | None:
    """Normalize Noise Figure Db to four decimal places."""
    return to_decimal(value, places=4)

def normalize_lifecycle_stage(value: object) -> AmplifierLifecycleStage | None:
    """Normalize Lifecycle Stage to its enum member (case and separator insensitive)."""
    return to_enum(AmplifierLifecycleStage, value)

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

def normalize_criticality(value: object) -> AmplifierCriticality | None:
    """Normalize Criticality to its enum member (case and separator insensitive)."""
    return to_enum(AmplifierCriticality, value)

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "systemId": normalize_system_id,
    "ampType": normalize_amp_type,
    "gainDb": normalize_gain_db,
    "outputPowerDbm": normalize_output_power_dbm,
    "noiseFigureDb": normalize_noise_figure_db,
    "lifecycleStage": normalize_lifecycle_stage,
    "sourceSystem": normalize_source_system,
    "region": normalize_region,
    "owner": normalize_owner,
    "criticality": normalize_criticality,
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
