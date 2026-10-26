"""Filter translation tests for Alarm Instance."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.alarm_instance.filters import AlarmInstanceFilter, to_query
from itta_api.domains.alarm.alarm_instance.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AlarmInstanceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AlarmInstanceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AlarmInstanceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AlarmInstanceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AlarmInstanceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AlarmInstanceFilter(external_ref=""))


def test_definition_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(AlarmInstanceFilter(definition_id=value))["definitionId"] == str(value)


def test_source_ref_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(source_ref="  abc ")) == {"sourceRef": "abc"}


def test_source_ref_blank_is_omitted():
    assert "sourceRef" not in to_query(AlarmInstanceFilter(source_ref=""))


def test_raised_at_window_is_iso_formatted():
    query = to_query(AlarmInstanceFilter(raised_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), raised_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["raisedAtFrom"].startswith("2026-01-01")
    assert query["raisedAtTo"].startswith("2026-01-01")


def test_cleared_at_window_is_iso_formatted():
    query = to_query(AlarmInstanceFilter(cleared_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), cleared_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["clearedAtFrom"].startswith("2026-01-01")
    assert query["clearedAtTo"].startswith("2026-01-01")


def test_acked_by_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(acked_by="  abc ")) == {"ackedBy": "abc"}


def test_acked_by_blank_is_omitted():
    assert "ackedBy" not in to_query(AlarmInstanceFilter(acked_by=""))


def test_alarm_state_values_are_comma_joined():
    members = list(AlarmInstanceAlarmState)[:2]
    query = to_query(AlarmInstanceFilter(alarm_state=members))
    assert query["alarmState"] == ",".join(m.value for m in members)


def test_effective_from_window_is_iso_formatted():
    query = to_query(AlarmInstanceFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(AlarmInstanceFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(AlarmInstanceFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(AlarmInstanceLifecycleStage)[:2]
    query = to_query(AlarmInstanceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(AlarmInstanceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(AlarmInstanceFilter(source_system=""))

