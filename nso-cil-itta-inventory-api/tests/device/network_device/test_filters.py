"""Filter translation tests for Network Device."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.device.network_device.filters import NetworkDeviceFilter, to_query
from itta_api.domains.device.network_device.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(NetworkDeviceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(NetworkDeviceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(NetworkDeviceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(NetworkDeviceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(NetworkDeviceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(NetworkDeviceFilter(external_ref=""))


def test_hostname_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(hostname="  abc ")) == {"hostname": "abc"}


def test_hostname_blank_is_omitted():
    assert "hostname" not in to_query(NetworkDeviceFilter(hostname=""))


def test_device_role_values_are_comma_joined():
    members = list(NetworkDeviceDeviceRole)[:2]
    query = to_query(NetworkDeviceFilter(device_role=members))
    assert query["deviceRole"] == ",".join(m.value for m in members)


def test_mgmt_ip_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(mgmt_ip="  abc ")) == {"mgmtIp": "abc"}


def test_mgmt_ip_blank_is_omitted():
    assert "mgmtIp" not in to_query(NetworkDeviceFilter(mgmt_ip=""))


def test_serial_number_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(NetworkDeviceFilter(serial_number=""))


def test_model_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(NetworkDeviceFilter(model_id=value))["modelId"] == str(value)


def test_rack_unit_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(NetworkDeviceFilter(rack_unit_id=value))["rackUnitId"] == str(value)


def test_effective_to_window_is_iso_formatted():
    query = to_query(NetworkDeviceFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(NetworkDeviceFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(NetworkDeviceLifecycleStage)[:2]
    query = to_query(NetworkDeviceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(NetworkDeviceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(NetworkDeviceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(NetworkDeviceFilter(region=""))

