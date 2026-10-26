"""Filter translation tests for Rack Unit."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.facility.rack_unit.filters import RackUnitFilter, to_query
from itta_api.domains.facility.rack_unit.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RackUnitFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RackUnitFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RackUnitFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RackUnitFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RackUnitFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RackUnitFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RackUnitFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RackUnitFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RackUnitFilter(external_ref=""))


def test_rack_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RackUnitFilter(rack_id=value))["rackId"] == str(value)


def test_unit_number_range_is_sent():
    query = to_query(RackUnitFilter(unit_number_min=1, unit_number_max=9))
    assert query["unitNumberMin"] == str(1)
    assert query["unitNumberMax"] == str(9)


def test_unit_number_zero_is_not_dropped():
    assert to_query(RackUnitFilter(unit_number_min=0))["unitNumberMin"] == "0"


def test_occupied_is_lowercase_boolean():
    assert to_query(RackUnitFilter(occupied=True))["occupied"] == "true"
    assert to_query(RackUnitFilter(occupied=False))["occupied"] == "false"


def test_mounted_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RackUnitFilter(mounted_device_id=value))["mountedDeviceId"] == str(value)


def test_face_values_are_comma_joined():
    members = list(RackUnitFace)[:2]
    query = to_query(RackUnitFilter(face=members))
    assert query["face"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(RackUnitFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(RackUnitFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(RackUnitFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(RackUnitFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(RackUnitFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(RackUnitFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(RackUnitCriticality)[:2]
    query = to_query(RackUnitFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(RackUnitFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(RackUnitFilter(cost_center=""))

