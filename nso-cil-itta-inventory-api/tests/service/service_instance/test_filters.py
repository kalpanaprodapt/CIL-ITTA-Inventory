"""Filter translation tests for Service Instance."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_instance.filters import ServiceInstanceFilter, to_query
from itta_api.domains.service.service_instance.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ServiceInstanceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ServiceInstanceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ServiceInstanceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ServiceInstanceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ServiceInstanceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ServiceInstanceFilter(external_ref=""))


def test_catalog_item_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceInstanceFilter(catalog_item_id=value))["catalogItemId"] == str(value)


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceInstanceFilter(customer_id=value))["customerId"] == str(value)


def test_activated_on_window_is_iso_formatted():
    query = to_query(ServiceInstanceFilter(activated_on_from=date(2026, 1, 1), activated_on_to=date(2026, 1, 1)))
    assert query["activatedOnFrom"].startswith("2026-01-01")
    assert query["activatedOnTo"].startswith("2026-01-01")


def test_bandwidth_mbps_range_is_sent():
    query = to_query(ServiceInstanceFilter(bandwidth_mbps_min=1, bandwidth_mbps_max=9))
    assert query["bandwidthMbpsMin"] == str(1)
    assert query["bandwidthMbpsMax"] == str(9)


def test_bandwidth_mbps_zero_is_not_dropped():
    assert to_query(ServiceInstanceFilter(bandwidth_mbps_min=0))["bandwidthMbpsMin"] == "0"


def test_service_state_values_are_comma_joined():
    members = list(ServiceInstanceServiceState)[:2]
    query = to_query(ServiceInstanceFilter(service_state=members))
    assert query["serviceState"] == ",".join(m.value for m in members)


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ServiceInstanceLifecycleStage)[:2]
    query = to_query(ServiceInstanceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ServiceInstanceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(ServiceInstanceFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(ServiceInstanceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(ServiceInstanceFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(ServiceInstanceCriticality)[:2]
    query = to_query(ServiceInstanceFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

