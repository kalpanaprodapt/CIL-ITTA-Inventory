"""Filter translation tests for Sector."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.sector.filters import SectorFilter, to_query
from itta_api.domains.radio.sector.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SectorFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SectorFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SectorFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SectorFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SectorFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SectorFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SectorFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SectorFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SectorFilter(external_ref=""))


def test_base_station_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SectorFilter(base_station_id=value))["baseStationId"] == str(value)


def test_sector_number_range_is_sent():
    query = to_query(SectorFilter(sector_number_min=1, sector_number_max=9))
    assert query["sectorNumberMin"] == str(1)
    assert query["sectorNumberMax"] == str(9)


def test_sector_number_zero_is_not_dropped():
    assert to_query(SectorFilter(sector_number_min=0))["sectorNumberMin"] == "0"


def test_pci_range_is_sent():
    query = to_query(SectorFilter(pci_min=1, pci_max=9))
    assert query["pciMin"] == str(1)
    assert query["pciMax"] == str(9)


def test_pci_zero_is_not_dropped():
    assert to_query(SectorFilter(pci_min=0))["pciMin"] == "0"


def test_frequency_mhz_range_is_sent():
    query = to_query(SectorFilter(frequency_mhz_min=1, frequency_mhz_max=9))
    assert query["frequencyMhzMin"] == str(1)
    assert query["frequencyMhzMax"] == str(9)


def test_frequency_mhz_zero_is_not_dropped():
    assert to_query(SectorFilter(frequency_mhz_min=0))["frequencyMhzMin"] == "0"


def test_bandwidth_mhz_range_is_sent():
    query = to_query(SectorFilter(bandwidth_mhz_min=1, bandwidth_mhz_max=9))
    assert query["bandwidthMhzMin"] == str(1)
    assert query["bandwidthMhzMax"] == str(9)


def test_bandwidth_mhz_zero_is_not_dropped():
    assert to_query(SectorFilter(bandwidth_mhz_min=0))["bandwidthMhzMin"] == "0"


def test_owner_is_trimmed_into_query():
    assert to_query(SectorFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SectorFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SectorCriticality)[:2]
    query = to_query(SectorFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SectorFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SectorFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SectorFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

