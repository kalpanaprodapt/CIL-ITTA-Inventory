"""Filter translation tests for Dns Record."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.dns_record.filters import DnsRecordFilter, to_query
from itta_api.domains.ipam.dns_record.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(DnsRecordFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(DnsRecordFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(DnsRecordFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(DnsRecordFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(DnsRecordFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(DnsRecordFilter(external_ref=""))


def test_zone_name_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(zone_name="  abc ")) == {"zoneName": "abc"}


def test_zone_name_blank_is_omitted():
    assert "zoneName" not in to_query(DnsRecordFilter(zone_name=""))


def test_record_type_values_are_comma_joined():
    members = list(DnsRecordRecordType)[:2]
    query = to_query(DnsRecordFilter(record_type=members))
    assert query["recordType"] == ",".join(m.value for m in members)


def test_record_value_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(record_value="  abc ")) == {"recordValue": "abc"}


def test_record_value_blank_is_omitted():
    assert "recordValue" not in to_query(DnsRecordFilter(record_value=""))


def test_ttl_range_is_sent():
    query = to_query(DnsRecordFilter(ttl_min=1, ttl_max=9))
    assert query["ttlMin"] == str(1)
    assert query["ttlMax"] == str(9)


def test_ttl_zero_is_not_dropped():
    assert to_query(DnsRecordFilter(ttl_min=0))["ttlMin"] == "0"


def test_source_system_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(DnsRecordFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(DnsRecordFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(DnsRecordFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(DnsRecordCriticality)[:2]
    query = to_query(DnsRecordFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(DnsRecordFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(DnsRecordFilter(cost_center=""))

