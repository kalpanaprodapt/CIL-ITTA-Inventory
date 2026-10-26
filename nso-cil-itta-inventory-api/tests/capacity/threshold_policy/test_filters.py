"""Filter translation tests for Threshold Policy."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.threshold_policy.filters import ThresholdPolicyFilter, to_query
from itta_api.domains.capacity.threshold_policy.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ThresholdPolicyFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ThresholdPolicyFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ThresholdPolicyFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ThresholdPolicyFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ThresholdPolicyFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ThresholdPolicyFilter(external_ref=""))


def test_policy_name_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(policy_name="  abc ")) == {"policyName": "abc"}


def test_policy_name_blank_is_omitted():
    assert "policyName" not in to_query(ThresholdPolicyFilter(policy_name=""))


def test_metric_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(metric="  abc ")) == {"metric": "abc"}


def test_metric_blank_is_omitted():
    assert "metric" not in to_query(ThresholdPolicyFilter(metric=""))


def test_warn_pct_range_is_sent():
    query = to_query(ThresholdPolicyFilter(warn_pct_min=Decimal("1.5"), warn_pct_max=Decimal("9.5")))
    assert query["warnPctMin"] == str(Decimal("1.5"))
    assert query["warnPctMax"] == str(Decimal("9.5"))


def test_warn_pct_zero_is_not_dropped():
    assert to_query(ThresholdPolicyFilter(warn_pct_min=0))["warnPctMin"] == "0"


def test_crit_pct_range_is_sent():
    query = to_query(ThresholdPolicyFilter(crit_pct_min=Decimal("1.5"), crit_pct_max=Decimal("9.5")))
    assert query["critPctMin"] == str(Decimal("1.5"))
    assert query["critPctMax"] == str(Decimal("9.5"))


def test_crit_pct_zero_is_not_dropped():
    assert to_query(ThresholdPolicyFilter(crit_pct_min=0))["critPctMin"] == "0"


def test_evaluation_window_minutes_range_is_sent():
    query = to_query(ThresholdPolicyFilter(evaluation_window_minutes_min=1, evaluation_window_minutes_max=9))
    assert query["evaluationWindowMinutesMin"] == str(1)
    assert query["evaluationWindowMinutesMax"] == str(9)


def test_evaluation_window_minutes_zero_is_not_dropped():
    assert to_query(ThresholdPolicyFilter(evaluation_window_minutes_min=0))["evaluationWindowMinutesMin"] == "0"


def test_region_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ThresholdPolicyFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ThresholdPolicyFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ThresholdPolicyCriticality)[:2]
    query = to_query(ThresholdPolicyFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ThresholdPolicyFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ThresholdPolicyFilter(cost_center=""))

