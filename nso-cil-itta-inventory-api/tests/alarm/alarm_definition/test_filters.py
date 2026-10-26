"""Filter translation tests for Alarm Definition."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.alarm_definition.filters import AlarmDefinitionFilter, to_query
from itta_api.domains.alarm.alarm_definition.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AlarmDefinitionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AlarmDefinitionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AlarmDefinitionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AlarmDefinitionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AlarmDefinitionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AlarmDefinitionFilter(external_ref=""))


def test_alarm_code_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(alarm_code="  abc ")) == {"alarmCode": "abc"}


def test_alarm_code_blank_is_omitted():
    assert "alarmCode" not in to_query(AlarmDefinitionFilter(alarm_code=""))


def test_severity_values_are_comma_joined():
    members = list(AlarmDefinitionSeverity)[:2]
    query = to_query(AlarmDefinitionFilter(severity=members))
    assert query["severity"] == ",".join(m.value for m in members)


def test_probable_cause_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(probable_cause="  abc ")) == {"probableCause": "abc"}


def test_probable_cause_blank_is_omitted():
    assert "probableCause" not in to_query(AlarmDefinitionFilter(probable_cause=""))


def test_auto_clear_is_lowercase_boolean():
    assert to_query(AlarmDefinitionFilter(auto_clear=True))["autoClear"] == "true"
    assert to_query(AlarmDefinitionFilter(auto_clear=False))["autoClear"] == "false"


def test_cost_center_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(AlarmDefinitionFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(AlarmDefinitionFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(AlarmDefinitionFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(AlarmDefinitionFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(AlarmDefinitionFilter(labels=""))

