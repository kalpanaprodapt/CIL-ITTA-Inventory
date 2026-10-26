"""Filter translation tests for Qos Policy."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.qos_policy.filters import QosPolicyFilter, to_query
from itta_api.domains.mpls.qos_policy.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(QosPolicyFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(QosPolicyFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(QosPolicyFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(QosPolicyFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(QosPolicyFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(QosPolicyFilter(external_ref=""))


def test_policy_name_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(policy_name="  abc ")) == {"policyName": "abc"}


def test_policy_name_blank_is_omitted():
    assert "policyName" not in to_query(QosPolicyFilter(policy_name=""))


def test_class_count_range_is_sent():
    query = to_query(QosPolicyFilter(class_count_min=1, class_count_max=9))
    assert query["classCountMin"] == str(1)
    assert query["classCountMax"] == str(9)


def test_class_count_zero_is_not_dropped():
    assert to_query(QosPolicyFilter(class_count_min=0))["classCountMin"] == "0"


def test_cir_mbps_range_is_sent():
    query = to_query(QosPolicyFilter(cir_mbps_min=1, cir_mbps_max=9))
    assert query["cirMbpsMin"] == str(1)
    assert query["cirMbpsMax"] == str(9)


def test_cir_mbps_zero_is_not_dropped():
    assert to_query(QosPolicyFilter(cir_mbps_min=0))["cirMbpsMin"] == "0"


def test_pir_mbps_range_is_sent():
    query = to_query(QosPolicyFilter(pir_mbps_min=1, pir_mbps_max=9))
    assert query["pirMbpsMin"] == str(1)
    assert query["pirMbpsMax"] == str(9)


def test_pir_mbps_zero_is_not_dropped():
    assert to_query(QosPolicyFilter(pir_mbps_min=0))["pirMbpsMin"] == "0"


def test_marking_scheme_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(marking_scheme="  abc ")) == {"markingScheme": "abc"}


def test_marking_scheme_blank_is_omitted():
    assert "markingScheme" not in to_query(QosPolicyFilter(marking_scheme=""))


def test_labels_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(QosPolicyFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(QosPolicyLifecycleStage)[:2]
    query = to_query(QosPolicyFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(QosPolicyFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(QosPolicyFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(QosPolicyFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(QosPolicyFilter(owner=""))

