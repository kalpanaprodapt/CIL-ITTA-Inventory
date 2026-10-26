from datetime import date, datetime, timezone
from decimal import Decimal
from enum import Enum
from uuid import UUID

import pytest

from itta_core import normalize as n


class Color(str, Enum):
    DARK_RED = "DARK_RED"
    BLUE = "BLUE"


@pytest.mark.parametrize("raw,expected", [("1", 1), (" 2 ", 2), ("1,000", 1000), ("", None), (None, None), ("x", None)])
def test_to_int(raw, expected):
    assert n.to_int(raw) == expected


@pytest.mark.parametrize("raw,expected", [("1.23456", Decimal("1.2346")), ("2", Decimal("2.0000")), ("", None), ("x", None)])
def test_to_decimal(raw, expected):
    assert n.to_decimal(raw) == expected


@pytest.mark.parametrize("raw,expected", [("yes", True), ("N", False), ("maybe", None), (True, True), (None, None)])
def test_to_bool(raw, expected):
    assert n.to_bool(raw) is expected


@pytest.mark.parametrize("raw", ["2026-03-04", "04/03/2026", "04-03-2026", "2026/03/04"])
def test_to_date_formats(raw):
    assert n.to_date(raw) == date(2026, 3, 4)


def test_to_datetime_assumes_utc():
    assert n.to_datetime("2026-03-04T10:00:00").tzinfo == timezone.utc


def test_to_datetime_keeps_offset():
    assert n.to_datetime("2026-03-04T10:00:00+02:00").utcoffset().total_seconds() == 7200


def test_to_uuid():
    assert n.to_uuid("00000000-0000-0000-0000-000000000011") == UUID(int=0x11)
    assert n.to_uuid("nope") is None


def test_to_enum_is_forgiving():
    assert n.to_enum(Color, "dark red") is Color.DARK_RED
    assert n.to_enum(Color, "dark-red") is Color.DARK_RED
    assert n.to_enum(Color, "green") is None


def test_collapse_whitespace():
    assert n.collapse_whitespace("  a \t b\n c ") == "a b c"


def test_camelize_keys():
    assert n.camelize_keys({"site_type": 1, "code": 2}) == {"siteType": 1, "code": 2}
