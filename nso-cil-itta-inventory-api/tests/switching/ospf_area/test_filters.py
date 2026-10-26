"""Filter translation tests for Ospf Area."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.switching.ospf_area.filters import OspfAreaFilter, to_query
from itta_api.domains.switching.ospf_area.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(OspfAreaFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(OspfAreaFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(OspfAreaFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(OspfAreaFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(OspfAreaFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(OspfAreaFilter(external_ref=""))


def test_area_id_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(area_id="  abc ")) == {"areaId": "abc"}


def test_area_id_blank_is_omitted():
    assert "areaId" not in to_query(OspfAreaFilter(area_id=""))


def test_area_type_values_are_comma_joined():
    members = list(OspfAreaAreaType)[:2]
    query = to_query(OspfAreaFilter(area_type=members))
    assert query["areaType"] == ",".join(m.value for m in members)


def test_device_count_range_is_sent():
    query = to_query(OspfAreaFilter(device_count_min=1, device_count_max=9))
    assert query["deviceCountMin"] == str(1)
    assert query["deviceCountMax"] == str(9)


def test_device_count_zero_is_not_dropped():
    assert to_query(OspfAreaFilter(device_count_min=0))["deviceCountMin"] == "0"


def test_auth_type_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(auth_type="  abc ")) == {"authType": "abc"}


def test_auth_type_blank_is_omitted():
    assert "authType" not in to_query(OspfAreaFilter(auth_type=""))


def test_criticality_values_are_comma_joined():
    members = list(OspfAreaCriticality)[:2]
    query = to_query(OspfAreaFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(OspfAreaFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(OspfAreaFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(OspfAreaFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(OspfAreaFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

