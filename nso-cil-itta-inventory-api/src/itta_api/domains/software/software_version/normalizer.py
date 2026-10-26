"""Normalisation of inbound Software Version records coming from external systems."""
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
from itta_api.domains.software.software_version.schemas import *  # noqa: F401,F403


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

def normalize_image_id(value: object) -> UUID | None:
    """Normalize Image Id to a UUID."""
    return to_uuid(value)

def normalize_version_string(value: object) -> str | None:
    """Normalize Version String: trim, collapse whitespace."""
    if value is None:
        return None
    text = collapse_whitespace(str(value))
    return text or None

def normalize_release_date(value: object) -> date | None:
    """Normalize Release Date from ISO or day-first strings."""
    return to_date(value)

def normalize_end_of_support(value: object) -> date | None:
    """Normalize End Of Support from ISO or day-first strings."""
    return to_date(value)

def normalize_release_train(value: object) -> SoftwareVersionReleaseTrain | None:
    """Normalize Release Train to its enum member (case and separator insensitive)."""
    return to_enum(SoftwareVersionReleaseTrain, value)

def normalize_cve_count(value: object) -> int | None:
    """Normalize Cve Count to an integer."""
    return to_int(value)

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

def normalize_criticality(value: object) -> SoftwareVersionCriticality | None:
    """Normalize Criticality to its enum member (case and separator insensitive)."""
    return to_enum(SoftwareVersionCriticality, value)

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

NORMALIZERS = {
    "code": normalize_code,
    "name": normalize_name,
    "description": normalize_description,
    "status": normalize_status,
    "externalRef": normalize_external_ref,
    "imageId": normalize_image_id,
    "versionString": normalize_version_string,
    "releaseDate": normalize_release_date,
    "endOfSupport": normalize_end_of_support,
    "releaseTrain": normalize_release_train,
    "cveCount": normalize_cve_count,
    "region": normalize_region,
    "owner": normalize_owner,
    "criticality": normalize_criticality,
    "costCenter": normalize_cost_center,
    "notes": normalize_notes,
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
