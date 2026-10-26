"""Filter translation tests for Ip Address."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.ip_address.filters import IpAddressFilter, to_query
from itta_api.domains.ipam.ip_address.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(IpAddressFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(IpAddressFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(IpAddressFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(IpAddressFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(IpAddressFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(IpAddressFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(IpAddressFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(IpAddressFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(IpAddressFilter(external_ref=""))


def test_subnet_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(IpAddressFilter(subnet_id=value))["subnetId"] == str(value)


def test_address_is_trimmed_into_query():
    assert to_query(IpAddressFilter(address="  abc ")) == {"address": "abc"}


def test_address_blank_is_omitted():
    assert "address" not in to_query(IpAddressFilter(address=""))


def test_assigned_to_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(IpAddressFilter(assigned_to_id=value))["assignedToId"] == str(value)


def test_assignment_type_values_are_comma_joined():
    members = list(IpAddressAssignmentType)[:2]
    query = to_query(IpAddressFilter(assignment_type=members))
    assert query["assignmentType"] == ",".join(m.value for m in members)


def test_dns_name_is_trimmed_into_query():
    assert to_query(IpAddressFilter(dns_name="  abc ")) == {"dnsName": "abc"}


def test_dns_name_blank_is_omitted():
    assert "dnsName" not in to_query(IpAddressFilter(dns_name=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(IpAddressFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(IpAddressFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(IpAddressFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(IpAddressFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(IpAddressFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(IpAddressFilter(labels=""))

