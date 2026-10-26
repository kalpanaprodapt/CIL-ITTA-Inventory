"""Base models shared by every domain."""
from __future__ import annotations

import re
from enum import Enum

from pydantic import BaseModel, ConfigDict
from pydantic.alias_generators import to_camel

_CONTROL = re.compile(r"[\x00-\x08\x0b\x0c\x0e-\x1f\x7f]")


class ApiModel(BaseModel):
    """Camel-case on the wire, snake_case in Python."""

    model_config = ConfigDict(
        alias_generator=to_camel,
        populate_by_name=True,
        extra="forbid",
        str_strip_whitespace=True,
        use_enum_values=False,
    )


class LifecycleStatus(str, Enum):
    PLANNED = "PLANNED"
    ACTIVE = "ACTIVE"
    MAINTENANCE = "MAINTENANCE"
    DECOMMISSIONED = "DECOMMISSIONED"


def reject_control_chars(value: str | None, field: str) -> str | None:
    if value is not None and _CONTROL.search(value):
        raise ValueError(f"{field} must not contain control characters")
    return value


class FieldMeta(ApiModel):
    name: str
    label: str
    type: str
    required: bool = False
    max_length: int | None = None
    enum_values: tuple[str, ...] = ()
    filterable: bool = True
    sortable: bool = True


class EntityMeta(ApiModel):
    entity: str
    title: str
    domain: str
    path: str
    fields: tuple[FieldMeta, ...]
