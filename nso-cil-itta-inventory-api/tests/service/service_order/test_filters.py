"""Filter translation tests for Service Order."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_order.filters import ServiceOrderFilter, to_query
from itta_api.domains.service.service_order.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ServiceOrderFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ServiceOrderFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ServiceOrderFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ServiceOrderFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ServiceOrderFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ServiceOrderFilter(external_ref=""))


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceOrderFilter(customer_id=value))["customerId"] == str(value)


def test_order_type_values_are_comma_joined():
    members = list(ServiceOrderOrderType)[:2]
    query = to_query(ServiceOrderFilter(order_type=members))
    assert query["orderType"] == ",".join(m.value for m in members)


def test_requested_on_window_is_iso_formatted():
    query = to_query(ServiceOrderFilter(requested_on_from=date(2026, 1, 1), requested_on_to=date(2026, 1, 1)))
    assert query["requestedOnFrom"].startswith("2026-01-01")
    assert query["requestedOnTo"].startswith("2026-01-01")


def test_target_date_window_is_iso_formatted():
    query = to_query(ServiceOrderFilter(target_date_from=date(2026, 1, 1), target_date_to=date(2026, 1, 1)))
    assert query["targetDateFrom"].startswith("2026-01-01")
    assert query["targetDateTo"].startswith("2026-01-01")


def test_priority_range_is_sent():
    query = to_query(ServiceOrderFilter(priority_min=1, priority_max=9))
    assert query["priorityMin"] == str(1)
    assert query["priorityMax"] == str(9)


def test_priority_zero_is_not_dropped():
    assert to_query(ServiceOrderFilter(priority_min=0))["priorityMin"] == "0"


def test_region_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ServiceOrderFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ServiceOrderFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ServiceOrderCriticality)[:2]
    query = to_query(ServiceOrderFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ServiceOrderFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ServiceOrderFilter(cost_center=""))

