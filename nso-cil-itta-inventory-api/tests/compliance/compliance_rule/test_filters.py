"""Filter translation tests for Compliance Rule."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.compliance_rule.filters import ComplianceRuleFilter, to_query
from itta_api.domains.compliance.compliance_rule.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ComplianceRuleFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ComplianceRuleFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ComplianceRuleFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ComplianceRuleFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ComplianceRuleFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ComplianceRuleFilter(external_ref=""))


def test_rule_code_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(rule_code="  abc ")) == {"ruleCode": "abc"}


def test_rule_code_blank_is_omitted():
    assert "ruleCode" not in to_query(ComplianceRuleFilter(rule_code=""))


def test_framework_values_are_comma_joined():
    members = list(ComplianceRuleFramework)[:2]
    query = to_query(ComplianceRuleFilter(framework=members))
    assert query["framework"] == ",".join(m.value for m in members)


def test_severity_values_are_comma_joined():
    members = list(ComplianceRuleSeverity)[:2]
    query = to_query(ComplianceRuleFilter(severity=members))
    assert query["severity"] == ",".join(m.value for m in members)


def test_region_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ComplianceRuleFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ComplianceRuleFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ComplianceRuleCriticality)[:2]
    query = to_query(ComplianceRuleFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ComplianceRuleFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ComplianceRuleFilter(cost_center=""))

