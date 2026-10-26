"""Filter translation tests for Vnf."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.virtual.vnf.filters import VnfFilter, to_query
from itta_api.domains.virtual.vnf.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VnfFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VnfFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VnfFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VnfFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VnfFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VnfFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VnfFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VnfFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VnfFilter(external_ref=""))


def test_vnf_name_is_trimmed_into_query():
    assert to_query(VnfFilter(vnf_name="  abc ")) == {"vnfName": "abc"}


def test_vnf_name_blank_is_omitted():
    assert "vnfName" not in to_query(VnfFilter(vnf_name=""))


def test_vnf_type_is_trimmed_into_query():
    assert to_query(VnfFilter(vnf_type="  abc ")) == {"vnfType": "abc"}


def test_vnf_type_blank_is_omitted():
    assert "vnfType" not in to_query(VnfFilter(vnf_type=""))


def test_flavor_is_trimmed_into_query():
    assert to_query(VnfFilter(flavor="  abc ")) == {"flavor": "abc"}


def test_flavor_blank_is_omitted():
    assert "flavor" not in to_query(VnfFilter(flavor=""))


def test_vcpu_range_is_sent():
    query = to_query(VnfFilter(vcpu_min=1, vcpu_max=9))
    assert query["vcpuMin"] == str(1)
    assert query["vcpuMax"] == str(9)


def test_vcpu_zero_is_not_dropped():
    assert to_query(VnfFilter(vcpu_min=0))["vcpuMin"] == "0"


def test_memory_gb_range_is_sent():
    query = to_query(VnfFilter(memory_gb_min=1, memory_gb_max=9))
    assert query["memoryGbMin"] == str(1)
    assert query["memoryGbMax"] == str(9)


def test_memory_gb_zero_is_not_dropped():
    assert to_query(VnfFilter(memory_gb_min=0))["memoryGbMin"] == "0"


def test_instance_count_range_is_sent():
    query = to_query(VnfFilter(instance_count_min=1, instance_count_max=9))
    assert query["instanceCountMin"] == str(1)
    assert query["instanceCountMax"] == str(9)


def test_instance_count_zero_is_not_dropped():
    assert to_query(VnfFilter(instance_count_min=0))["instanceCountMin"] == "0"


def test_lifecycle_stage_values_are_comma_joined():
    members = list(VnfLifecycleStage)[:2]
    query = to_query(VnfFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(VnfFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(VnfFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(VnfFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(VnfFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(VnfFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(VnfFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(VnfCriticality)[:2]
    query = to_query(VnfFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

