from decimal import Decimal
from enum import Enum
from uuid import UUID

from itta_core.diffing import Action, compare_decimal, compare_value, plan_for


class Kind(str, Enum):
    A = "A"


def test_missing_incoming_is_ignored():
    assert compare_value("x", "a", None) == []


def test_equal_values_have_no_diff():
    assert compare_value("x", "a", "a") == []


def test_enum_compares_by_value():
    assert compare_value("x", Kind.A, "A") == []


def test_blank_vs_blank_is_no_diff():
    assert compare_value("x", None, "  ") == []


def test_difference_is_reported():
    diff = compare_value("x", "a", "b")[0]
    assert (diff.field, diff.current, diff.incoming) == ("x", "a", "b")


def test_decimal_tolerance():
    assert compare_decimal("d", Decimal("1.00001"), Decimal("1.0"), Decimal("0.0001")) == []
    assert compare_decimal("d", Decimal("1.1"), Decimal("1.0"), Decimal("0.0001"))


def test_plan_for_noop_and_update():
    target = UUID(int=1)
    assert plan_for("k", target, [], {}).action == Action.NOOP
    diffs = compare_value("x", 1, 2)
    assert plan_for("k", target, diffs, {"x": 2}).action == Action.UPDATE
