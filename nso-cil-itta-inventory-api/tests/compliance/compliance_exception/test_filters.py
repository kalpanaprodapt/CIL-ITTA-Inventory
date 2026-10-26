"""Filter translation tests for Compliance Exception."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.compliance_exception.filters import ComplianceExceptionFilter, to_query
from itta_api.domains.compliance.compliance_exception.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ComplianceExceptionFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ComplianceExceptionFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ComplianceExceptionFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ComplianceExceptionFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ComplianceExceptionFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ComplianceExceptionFilter(external_ref=""))


def test_rule_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ComplianceExceptionFilter(rule_id=value))["ruleId"] == str(value)


def test_target_ref_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(target_ref="  abc ")) == {"targetRef": "abc"}


def test_target_ref_blank_is_omitted():
    assert "targetRef" not in to_query(ComplianceExceptionFilter(target_ref=""))


def test_approved_by_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(approved_by="  abc ")) == {"approvedBy": "abc"}


def test_approved_by_blank_is_omitted():
    assert "approvedBy" not in to_query(ComplianceExceptionFilter(approved_by=""))


def test_expires_on_window_is_iso_formatted():
    query = to_query(ComplianceExceptionFilter(expires_on_from=date(2026, 1, 1), expires_on_to=date(2026, 1, 1)))
    assert query["expiresOnFrom"].startswith("2026-01-01")
    assert query["expiresOnTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ComplianceExceptionFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ComplianceExceptionFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ComplianceExceptionLifecycleStage)[:2]
    query = to_query(ComplianceExceptionFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ComplianceExceptionFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ComplianceExceptionFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ComplianceExceptionFilter(region=""))

