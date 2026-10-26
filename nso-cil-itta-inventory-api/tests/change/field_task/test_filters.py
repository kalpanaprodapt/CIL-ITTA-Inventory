"""Filter translation tests for Field Task."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.field_task.filters import FieldTaskFilter, to_query
from itta_api.domains.change.field_task.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(FieldTaskFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(FieldTaskFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(FieldTaskFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(FieldTaskFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(FieldTaskFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(FieldTaskFilter(external_ref=""))


def test_work_order_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(FieldTaskFilter(work_order_id=value))["workOrderId"] == str(value)


def test_task_name_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(task_name="  abc ")) == {"taskName": "abc"}


def test_task_name_blank_is_omitted():
    assert "taskName" not in to_query(FieldTaskFilter(task_name=""))


def test_sequence_range_is_sent():
    query = to_query(FieldTaskFilter(sequence_min=1, sequence_max=9))
    assert query["sequenceMin"] == str(1)
    assert query["sequenceMax"] == str(9)


def test_sequence_zero_is_not_dropped():
    assert to_query(FieldTaskFilter(sequence_min=0))["sequenceMin"] == "0"


def test_duration_minutes_range_is_sent():
    query = to_query(FieldTaskFilter(duration_minutes_min=1, duration_minutes_max=9))
    assert query["durationMinutesMin"] == str(1)
    assert query["durationMinutesMax"] == str(9)


def test_duration_minutes_zero_is_not_dropped():
    assert to_query(FieldTaskFilter(duration_minutes_min=0))["durationMinutesMin"] == "0"


def test_completed_on_window_is_iso_formatted():
    query = to_query(FieldTaskFilter(completed_on_from=datetime(2026, 1, 1, tzinfo=timezone.utc), completed_on_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["completedOnFrom"].startswith("2026-01-01")
    assert query["completedOnTo"].startswith("2026-01-01")


def test_region_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(FieldTaskFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(FieldTaskFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(FieldTaskCriticality)[:2]
    query = to_query(FieldTaskFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(FieldTaskFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(FieldTaskFilter(cost_center=""))

