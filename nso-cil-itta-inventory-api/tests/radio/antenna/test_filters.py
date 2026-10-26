"""Filter translation tests for Antenna."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.antenna.filters import AntennaFilter, to_query
from itta_api.domains.radio.antenna.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AntennaFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AntennaFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AntennaFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AntennaFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AntennaFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AntennaFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AntennaFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AntennaFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AntennaFilter(external_ref=""))


def test_sector_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(AntennaFilter(sector_id=value))["sectorId"] == str(value)


def test_model_is_trimmed_into_query():
    assert to_query(AntennaFilter(model="  abc ")) == {"model": "abc"}


def test_model_blank_is_omitted():
    assert "model" not in to_query(AntennaFilter(model=""))


def test_height_m_range_is_sent():
    query = to_query(AntennaFilter(height_m_min=Decimal("1.5"), height_m_max=Decimal("9.5")))
    assert query["heightMMin"] == str(Decimal("1.5"))
    assert query["heightMMax"] == str(Decimal("9.5"))


def test_height_m_zero_is_not_dropped():
    assert to_query(AntennaFilter(height_m_min=0))["heightMMin"] == "0"


def test_azimuth_deg_range_is_sent():
    query = to_query(AntennaFilter(azimuth_deg_min=Decimal("1.5"), azimuth_deg_max=Decimal("9.5")))
    assert query["azimuthDegMin"] == str(Decimal("1.5"))
    assert query["azimuthDegMax"] == str(Decimal("9.5"))


def test_azimuth_deg_zero_is_not_dropped():
    assert to_query(AntennaFilter(azimuth_deg_min=0))["azimuthDegMin"] == "0"


def test_tilt_deg_range_is_sent():
    query = to_query(AntennaFilter(tilt_deg_min=Decimal("1.5"), tilt_deg_max=Decimal("9.5")))
    assert query["tiltDegMin"] == str(Decimal("1.5"))
    assert query["tiltDegMax"] == str(Decimal("9.5"))


def test_tilt_deg_zero_is_not_dropped():
    assert to_query(AntennaFilter(tilt_deg_min=0))["tiltDegMin"] == "0"


def test_gain_dbi_range_is_sent():
    query = to_query(AntennaFilter(gain_dbi_min=Decimal("1.5"), gain_dbi_max=Decimal("9.5")))
    assert query["gainDbiMin"] == str(Decimal("1.5"))
    assert query["gainDbiMax"] == str(Decimal("9.5"))


def test_gain_dbi_zero_is_not_dropped():
    assert to_query(AntennaFilter(gain_dbi_min=0))["gainDbiMin"] == "0"


def test_source_system_is_trimmed_into_query():
    assert to_query(AntennaFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(AntennaFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(AntennaFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(AntennaFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(AntennaFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(AntennaFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(AntennaCriticality)[:2]
    query = to_query(AntennaFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(AntennaFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(AntennaFilter(cost_center=""))

