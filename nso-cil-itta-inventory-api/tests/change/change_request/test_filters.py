"""Filter translation tests for Change Request."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.change_request.filters import ChangeRequestFilter, to_query
from itta_api.domains.change.change_request.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ChangeRequestFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ChangeRequestFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ChangeRequestFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ChangeRequestFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ChangeRequestFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ChangeRequestFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ChangeRequestFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ChangeRequestFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ChangeRequestFilter(external_ref=""))


def test_change_number_is_trimmed_into_query():
    assert to_query(ChangeRequestFilter(change_number="  abc ")) == {"changeNumber": "abc"}


def test_change_number_blank_is_omitted():
    assert "changeNumber" not in to_query(ChangeRequestFilter(change_number=""))


def test_change_type_values_are_comma_joined():
    members = list(ChangeRequestChangeType)[:2]
    query = to_query(ChangeRequestFilter(change_type=members))
    assert query["changeType"] == ",".join(m.value for m in members)


def test_risk_level_values_are_comma_joined():
    members = list(ChangeRequestRiskLevel)[:2]
    query = to_query(ChangeRequestFilter(risk_level=members))
    assert query["riskLevel"] == ",".join(m.value for m in members)


def test_planned_start_window_is_iso_formatted():
    query = to_query(ChangeRequestFilter(planned_start_from=datetime(2026, 1, 1, tzinfo=timezone.utc), planned_start_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["plannedStartFrom"].startswith("2026-01-01")
    assert query["plannedStartTo"].startswith("2026-01-01")


def test_planned_end_window_is_iso_formatted():
    query = to_query(ChangeRequestFilter(planned_end_from=datetime(2026, 1, 1, tzinfo=timezone.utc), planned_end_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["plannedEndFrom"].startswith("2026-01-01")
    assert query["plannedEndTo"].startswith("2026-01-01")


def test_effective_from_window_is_iso_formatted():
    query = to_query(ChangeRequestFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ChangeRequestFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ChangeRequestFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ChangeRequestFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ChangeRequestLifecycleStage)[:2]
    query = to_query(ChangeRequestFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

