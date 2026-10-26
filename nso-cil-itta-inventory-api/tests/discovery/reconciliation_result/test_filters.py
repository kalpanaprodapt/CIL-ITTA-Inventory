"""Filter translation tests for Reconciliation Result."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.reconciliation_result.filters import ReconciliationResultFilter, to_query
from itta_api.domains.discovery.reconciliation_result.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ReconciliationResultFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ReconciliationResultFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ReconciliationResultFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ReconciliationResultFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ReconciliationResultFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ReconciliationResultFilter(external_ref=""))


def test_job_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ReconciliationResultFilter(job_id=value))["jobId"] == str(value)


def test_rule_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ReconciliationResultFilter(rule_id=value))["ruleId"] == str(value)


def test_outcome_values_are_comma_joined():
    members = list(ReconciliationResultOutcome)[:2]
    query = to_query(ReconciliationResultFilter(outcome=members))
    assert query["outcome"] == ",".join(m.value for m in members)


def test_resolved_by_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(resolved_by="  abc ")) == {"resolvedBy": "abc"}


def test_resolved_by_blank_is_omitted():
    assert "resolvedBy" not in to_query(ReconciliationResultFilter(resolved_by=""))


def test_source_system_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ReconciliationResultFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ReconciliationResultFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ReconciliationResultFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ReconciliationResultCriticality)[:2]
    query = to_query(ReconciliationResultFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ReconciliationResultFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ReconciliationResultFilter(cost_center=""))

