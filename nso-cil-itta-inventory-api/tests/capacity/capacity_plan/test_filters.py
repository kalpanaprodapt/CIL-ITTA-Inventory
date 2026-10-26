"""Filter translation tests for Capacity Plan."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.capacity_plan.filters import CapacityPlanFilter, to_query
from itta_api.domains.capacity.capacity_plan.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CapacityPlanFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CapacityPlanFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CapacityPlanFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CapacityPlanFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CapacityPlanFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CapacityPlanFilter(external_ref=""))


def test_plan_name_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(plan_name="  abc ")) == {"planName": "abc"}


def test_plan_name_blank_is_omitted():
    assert "planName" not in to_query(CapacityPlanFilter(plan_name=""))


def test_horizon_months_range_is_sent():
    query = to_query(CapacityPlanFilter(horizon_months_min=1, horizon_months_max=9))
    assert query["horizonMonthsMin"] == str(1)
    assert query["horizonMonthsMax"] == str(9)


def test_horizon_months_zero_is_not_dropped():
    assert to_query(CapacityPlanFilter(horizon_months_min=0))["horizonMonthsMin"] == "0"


def test_target_utilization_pct_range_is_sent():
    query = to_query(CapacityPlanFilter(target_utilization_pct_min=Decimal("1.5"), target_utilization_pct_max=Decimal("9.5")))
    assert query["targetUtilizationPctMin"] == str(Decimal("1.5"))
    assert query["targetUtilizationPctMax"] == str(Decimal("9.5"))


def test_target_utilization_pct_zero_is_not_dropped():
    assert to_query(CapacityPlanFilter(target_utilization_pct_min=0))["targetUtilizationPctMin"] == "0"


def test_owner_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CapacityPlanFilter(owner=""))


def test_approved_on_window_is_iso_formatted():
    query = to_query(CapacityPlanFilter(approved_on_from=date(2026, 1, 1), approved_on_to=date(2026, 1, 1)))
    assert query["approvedOnFrom"].startswith("2026-01-01")
    assert query["approvedOnTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CapacityPlanFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CapacityPlanFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CapacityPlanLifecycleStage)[:2]
    query = to_query(CapacityPlanFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CapacityPlanFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(CapacityPlanFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(CapacityPlanFilter(region=""))

