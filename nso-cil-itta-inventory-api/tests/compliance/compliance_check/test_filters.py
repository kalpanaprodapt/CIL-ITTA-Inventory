"""Filter translation tests for Compliance Check."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.compliance_check.filters import ComplianceCheckFilter, to_query
from itta_api.domains.compliance.compliance_check.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ComplianceCheckFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ComplianceCheckFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ComplianceCheckFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ComplianceCheckFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ComplianceCheckFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ComplianceCheckFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ComplianceCheckFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ComplianceCheckFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ComplianceCheckFilter(external_ref=""))


def test_rule_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ComplianceCheckFilter(rule_id=value))["ruleId"] == str(value)


def test_target_ref_is_trimmed_into_query():
    assert to_query(ComplianceCheckFilter(target_ref="  abc ")) == {"targetRef": "abc"}


def test_target_ref_blank_is_omitted():
    assert "targetRef" not in to_query(ComplianceCheckFilter(target_ref=""))


def test_checked_at_window_is_iso_formatted():
    query = to_query(ComplianceCheckFilter(checked_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), checked_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["checkedAtFrom"].startswith("2026-01-01")
    assert query["checkedAtTo"].startswith("2026-01-01")


def test_result_values_are_comma_joined():
    members = list(ComplianceCheckResult)[:2]
    query = to_query(ComplianceCheckFilter(result=members))
    assert query["result"] == ",".join(m.value for m in members)


def test_criticality_values_are_comma_joined():
    members = list(ComplianceCheckCriticality)[:2]
    query = to_query(ComplianceCheckFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ComplianceCheckFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ComplianceCheckFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(ComplianceCheckFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ComplianceCheckFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

