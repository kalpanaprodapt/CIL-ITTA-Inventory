"""Filter translation tests for Depreciation Schedule."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.assetmgmt.depreciation_schedule.filters import DepreciationScheduleFilter, to_query
from itta_api.domains.assetmgmt.depreciation_schedule.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(DepreciationScheduleFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(DepreciationScheduleFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(DepreciationScheduleFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(DepreciationScheduleFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(DepreciationScheduleFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(DepreciationScheduleFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(DepreciationScheduleFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(DepreciationScheduleFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(DepreciationScheduleFilter(external_ref=""))


def test_asset_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(DepreciationScheduleFilter(asset_id=value))["assetId"] == str(value)


def test_method_values_are_comma_joined():
    members = list(DepreciationScheduleMethod)[:2]
    query = to_query(DepreciationScheduleFilter(method=members))
    assert query["method"] == ",".join(m.value for m in members)


def test_useful_life_months_range_is_sent():
    query = to_query(DepreciationScheduleFilter(useful_life_months_min=1, useful_life_months_max=9))
    assert query["usefulLifeMonthsMin"] == str(1)
    assert query["usefulLifeMonthsMax"] == str(9)


def test_useful_life_months_zero_is_not_dropped():
    assert to_query(DepreciationScheduleFilter(useful_life_months_min=0))["usefulLifeMonthsMin"] == "0"


def test_salvage_value_range_is_sent():
    query = to_query(DepreciationScheduleFilter(salvage_value_min=Decimal("1.5"), salvage_value_max=Decimal("9.5")))
    assert query["salvageValueMin"] == str(Decimal("1.5"))
    assert query["salvageValueMax"] == str(Decimal("9.5"))


def test_salvage_value_zero_is_not_dropped():
    assert to_query(DepreciationScheduleFilter(salvage_value_min=0))["salvageValueMin"] == "0"


def test_start_date_window_is_iso_formatted():
    query = to_query(DepreciationScheduleFilter(start_date_from=date(2026, 1, 1), start_date_to=date(2026, 1, 1)))
    assert query["startDateFrom"].startswith("2026-01-01")
    assert query["startDateTo"].startswith("2026-01-01")


def test_effective_from_window_is_iso_formatted():
    query = to_query(DepreciationScheduleFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(DepreciationScheduleFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(DepreciationScheduleFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(DepreciationScheduleFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(DepreciationScheduleLifecycleStage)[:2]
    query = to_query(DepreciationScheduleFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(DepreciationScheduleFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(DepreciationScheduleFilter(source_system=""))

