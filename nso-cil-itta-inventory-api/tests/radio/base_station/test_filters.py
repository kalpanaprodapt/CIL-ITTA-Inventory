"""Filter translation tests for Base Station."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.base_station.filters import BaseStationFilter, to_query
from itta_api.domains.radio.base_station.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(BaseStationFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(BaseStationFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(BaseStationFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(BaseStationFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(BaseStationFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(BaseStationFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(BaseStationFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(BaseStationFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(BaseStationFilter(external_ref=""))


def test_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(BaseStationFilter(site_id=value))["siteId"] == str(value)


def test_enb_id_range_is_sent():
    query = to_query(BaseStationFilter(enb_id_min=1, enb_id_max=9))
    assert query["enbIdMin"] == str(1)
    assert query["enbIdMax"] == str(9)


def test_enb_id_zero_is_not_dropped():
    assert to_query(BaseStationFilter(enb_id_min=0))["enbIdMin"] == "0"


def test_technology_values_are_comma_joined():
    members = list(BaseStationTechnology)[:2]
    query = to_query(BaseStationFilter(technology=members))
    assert query["technology"] == ",".join(m.value for m in members)


def test_sector_count_range_is_sent():
    query = to_query(BaseStationFilter(sector_count_min=1, sector_count_max=9))
    assert query["sectorCountMin"] == str(1)
    assert query["sectorCountMax"] == str(9)


def test_sector_count_zero_is_not_dropped():
    assert to_query(BaseStationFilter(sector_count_min=0))["sectorCountMin"] == "0"


def test_cell_count_range_is_sent():
    query = to_query(BaseStationFilter(cell_count_min=1, cell_count_max=9))
    assert query["cellCountMin"] == str(1)
    assert query["cellCountMax"] == str(9)


def test_cell_count_zero_is_not_dropped():
    assert to_query(BaseStationFilter(cell_count_min=0))["cellCountMin"] == "0"


def test_labels_is_trimmed_into_query():
    assert to_query(BaseStationFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(BaseStationFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(BaseStationLifecycleStage)[:2]
    query = to_query(BaseStationFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(BaseStationFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(BaseStationFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(BaseStationFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(BaseStationFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(BaseStationFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(BaseStationFilter(owner=""))

