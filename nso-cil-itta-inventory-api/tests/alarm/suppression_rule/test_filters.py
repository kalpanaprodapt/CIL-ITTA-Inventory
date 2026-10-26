"""Filter translation tests for Suppression Rule."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.suppression_rule.filters import SuppressionRuleFilter, to_query
from itta_api.domains.alarm.suppression_rule.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SuppressionRuleFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SuppressionRuleFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SuppressionRuleFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SuppressionRuleFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SuppressionRuleFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SuppressionRuleFilter(external_ref=""))


def test_rule_name_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(rule_name="  abc ")) == {"ruleName": "abc"}


def test_rule_name_blank_is_omitted():
    assert "ruleName" not in to_query(SuppressionRuleFilter(rule_name=""))


def test_starts_at_window_is_iso_formatted():
    query = to_query(SuppressionRuleFilter(starts_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), starts_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["startsAtFrom"].startswith("2026-01-01")
    assert query["startsAtTo"].startswith("2026-01-01")


def test_ends_at_window_is_iso_formatted():
    query = to_query(SuppressionRuleFilter(ends_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), ends_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["endsAtFrom"].startswith("2026-01-01")
    assert query["endsAtTo"].startswith("2026-01-01")


def test_reason_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(reason="  abc ")) == {"reason": "abc"}


def test_reason_blank_is_omitted():
    assert "reason" not in to_query(SuppressionRuleFilter(reason=""))


def test_source_system_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(SuppressionRuleFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SuppressionRuleFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SuppressionRuleFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SuppressionRuleCriticality)[:2]
    query = to_query(SuppressionRuleFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SuppressionRuleFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SuppressionRuleFilter(cost_center=""))

