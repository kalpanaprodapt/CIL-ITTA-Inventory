"""Filter translation tests for Optical Channel."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.optical_channel.filters import OpticalChannelFilter, to_query
from itta_api.domains.optical.optical_channel.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(OpticalChannelFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(OpticalChannelFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(OpticalChannelFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(OpticalChannelFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(OpticalChannelFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(OpticalChannelFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(OpticalChannelFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(OpticalChannelFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(OpticalChannelFilter(external_ref=""))


def test_wavelength_nm_range_is_sent():
    query = to_query(OpticalChannelFilter(wavelength_nm_min=Decimal("1.5"), wavelength_nm_max=Decimal("9.5")))
    assert query["wavelengthNmMin"] == str(Decimal("1.5"))
    assert query["wavelengthNmMax"] == str(Decimal("9.5"))


def test_wavelength_nm_zero_is_not_dropped():
    assert to_query(OpticalChannelFilter(wavelength_nm_min=0))["wavelengthNmMin"] == "0"


def test_channel_number_range_is_sent():
    query = to_query(OpticalChannelFilter(channel_number_min=1, channel_number_max=9))
    assert query["channelNumberMin"] == str(1)
    assert query["channelNumberMax"] == str(9)


def test_channel_number_zero_is_not_dropped():
    assert to_query(OpticalChannelFilter(channel_number_min=0))["channelNumberMin"] == "0"


def test_grid_type_values_are_comma_joined():
    members = list(OpticalChannelGridType)[:2]
    query = to_query(OpticalChannelFilter(grid_type=members))
    assert query["gridType"] == ",".join(m.value for m in members)


def test_bitrate_gbps_range_is_sent():
    query = to_query(OpticalChannelFilter(bitrate_gbps_min=Decimal("1.5"), bitrate_gbps_max=Decimal("9.5")))
    assert query["bitrateGbpsMin"] == str(Decimal("1.5"))
    assert query["bitrateGbpsMax"] == str(Decimal("9.5"))


def test_bitrate_gbps_zero_is_not_dropped():
    assert to_query(OpticalChannelFilter(bitrate_gbps_min=0))["bitrateGbpsMin"] == "0"


def test_system_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(OpticalChannelFilter(system_id=value))["systemId"] == str(value)


def test_criticality_values_are_comma_joined():
    members = list(OpticalChannelCriticality)[:2]
    query = to_query(OpticalChannelFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(OpticalChannelFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(OpticalChannelFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(OpticalChannelFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(OpticalChannelFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

