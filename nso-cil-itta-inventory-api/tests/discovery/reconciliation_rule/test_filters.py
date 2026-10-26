"""Filter translation tests for Reconciliation Rule."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.reconciliation_rule.filters import ReconciliationRuleFilter, to_query
from itta_api.domains.discovery.reconciliation_rule.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ReconciliationRuleFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ReconciliationRuleFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ReconciliationRuleFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ReconciliationRuleFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ReconciliationRuleFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ReconciliationRuleFilter(external_ref=""))


def test_rule_name_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(rule_name="  abc ")) == {"ruleName": "abc"}


def test_rule_name_blank_is_omitted():
    assert "ruleName" not in to_query(ReconciliationRuleFilter(rule_name=""))


def test_match_key_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(match_key="  abc ")) == {"matchKey": "abc"}


def test_match_key_blank_is_omitted():
    assert "matchKey" not in to_query(ReconciliationRuleFilter(match_key=""))


def test_action_values_are_comma_joined():
    members = list(ReconciliationRuleAction)[:2]
    query = to_query(ReconciliationRuleFilter(action=members))
    assert query["action"] == ",".join(m.value for m in members)


def test_priority_range_is_sent():
    query = to_query(ReconciliationRuleFilter(priority_min=1, priority_max=9))
    assert query["priorityMin"] == str(1)
    assert query["priorityMax"] == str(9)


def test_priority_zero_is_not_dropped():
    assert to_query(ReconciliationRuleFilter(priority_min=0))["priorityMin"] == "0"


def test_labels_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ReconciliationRuleFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ReconciliationRuleLifecycleStage)[:2]
    query = to_query(ReconciliationRuleFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ReconciliationRuleFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ReconciliationRuleFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ReconciliationRuleFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ReconciliationRuleFilter(owner=""))

