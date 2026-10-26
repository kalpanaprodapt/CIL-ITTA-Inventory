"""Value coercion helpers used by the per-entity normalisers."""
from __future__ import annotations

import re
from datetime import date, datetime, timezone
from decimal import ROUND_HALF_UP, Decimal, InvalidOperation
from enum import Enum
from typing import TypeVar
from uuid import UUID

E = TypeVar("E", bound=Enum)
_WS = re.compile(r"\s+")
_TRUE = {"1", "true", "t", "yes", "y", "on"}
_FALSE = {"0", "false", "f", "no", "n", "off"}


def collapse_whitespace(value: str) -> str:
    return _WS.sub(" ", value).strip()


def to_int(value: object) -> int | None:
    if value is None or (isinstance(value, str) and not value.strip()):
        return None
    if isinstance(value, bool):
        return int(value)
    try:
        return int(Decimal(str(value).strip().replace(",", "")))
    except (InvalidOperation, ValueError):
        return None


def to_decimal(value: object, places: int = 4) -> Decimal | None:
    if value is None or (isinstance(value, str) and not value.strip()):
        return None
    try:
        number = Decimal(str(value).strip().replace(",", ""))
    except InvalidOperation:
        return None
    return number.quantize(Decimal(1).scaleb(-places), rounding=ROUND_HALF_UP)


def to_bool(value: object) -> bool | None:
    if isinstance(value, bool):
        return value
    if value is None:
        return None
    text = str(value).strip().lower()
    if text in _TRUE:
        return True
    if text in _FALSE:
        return False
    return None


def to_date(value: object) -> date | None:
    if isinstance(value, datetime):
        return value.date()
    if isinstance(value, date):
        return value
    if not isinstance(value, str) or not value.strip():
        return None
    text = value.strip()
    for fmt in ("%Y-%m-%d", "%d/%m/%Y", "%d-%m-%Y", "%Y/%m/%d"):
        try:
            return datetime.strptime(text, fmt).date()
        except ValueError:
            continue
    return None


def to_datetime(value: object) -> datetime | None:
    if isinstance(value, datetime):
        return value if value.tzinfo else value.replace(tzinfo=timezone.utc)
    if not isinstance(value, str) or not value.strip():
        return None
    text = value.strip().replace("Z", "+00:00")
    try:
        parsed = datetime.fromisoformat(text)
    except ValueError:
        return None
    return parsed if parsed.tzinfo else parsed.replace(tzinfo=timezone.utc)


def to_uuid(value: object) -> UUID | None:
    if isinstance(value, UUID):
        return value
    if value is None:
        return None
    try:
        return UUID(str(value).strip())
    except ValueError:
        return None


def to_enum(enum_cls: type[E], value: object) -> E | None:
    if isinstance(value, enum_cls):
        return value
    if value is None:
        return None
    key = re.sub(r"[\s\-]+", "_", str(value).strip()).upper()
    try:
        return enum_cls[key]
    except KeyError:
        return None


def camelize_keys(raw: dict[str, object]) -> dict[str, object]:
    out: dict[str, object] = {}
    for key, value in raw.items():
        parts = str(key).split("_")
        out[parts[0] + "".join(p.title() for p in parts[1:])] = value
    return out
