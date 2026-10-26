"""Filter translation tests for Audit Record."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.audit_record.filters import AuditRecordFilter, to_query
from itta_api.domains.compliance.audit_record.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AuditRecordFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AuditRecordFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AuditRecordFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AuditRecordFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AuditRecordFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AuditRecordFilter(external_ref=""))


def test_actor_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(actor="  abc ")) == {"actor": "abc"}


def test_actor_blank_is_omitted():
    assert "actor" not in to_query(AuditRecordFilter(actor=""))


def test_action_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(action="  abc ")) == {"action": "abc"}


def test_action_blank_is_omitted():
    assert "action" not in to_query(AuditRecordFilter(action=""))


def test_entity_ref_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(entity_ref="  abc ")) == {"entityRef": "abc"}


def test_entity_ref_blank_is_omitted():
    assert "entityRef" not in to_query(AuditRecordFilter(entity_ref=""))


def test_occurred_at_window_is_iso_formatted():
    query = to_query(AuditRecordFilter(occurred_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), occurred_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["occurredAtFrom"].startswith("2026-01-01")
    assert query["occurredAtTo"].startswith("2026-01-01")


def test_effective_from_window_is_iso_formatted():
    query = to_query(AuditRecordFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(AuditRecordFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(AuditRecordFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(AuditRecordFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(AuditRecordLifecycleStage)[:2]
    query = to_query(AuditRecordFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

