"""Filter translation tests for Fiber Strand."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.fiber_strand.filters import FiberStrandFilter, to_query
from itta_api.domains.cabling.fiber_strand.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(FiberStrandFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(FiberStrandFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(FiberStrandFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(FiberStrandFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(FiberStrandFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(FiberStrandFilter(external_ref=""))


def test_cable_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(FiberStrandFilter(cable_id=value))["cableId"] == str(value)


def test_strand_number_range_is_sent():
    query = to_query(FiberStrandFilter(strand_number_min=1, strand_number_max=9))
    assert query["strandNumberMin"] == str(1)
    assert query["strandNumberMax"] == str(9)


def test_strand_number_zero_is_not_dropped():
    assert to_query(FiberStrandFilter(strand_number_min=0))["strandNumberMin"] == "0"


def test_buffer_tube_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(buffer_tube="  abc ")) == {"bufferTube": "abc"}


def test_buffer_tube_blank_is_omitted():
    assert "bufferTube" not in to_query(FiberStrandFilter(buffer_tube=""))


def test_tube_color_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(tube_color="  abc ")) == {"tubeColor": "abc"}


def test_tube_color_blank_is_omitted():
    assert "tubeColor" not in to_query(FiberStrandFilter(tube_color=""))


def test_attenuation_db_range_is_sent():
    query = to_query(FiberStrandFilter(attenuation_db_min=Decimal("1.5"), attenuation_db_max=Decimal("9.5")))
    assert query["attenuationDbMin"] == str(Decimal("1.5"))
    assert query["attenuationDbMax"] == str(Decimal("9.5"))


def test_attenuation_db_zero_is_not_dropped():
    assert to_query(FiberStrandFilter(attenuation_db_min=0))["attenuationDbMin"] == "0"


def test_in_use_is_lowercase_boolean():
    assert to_query(FiberStrandFilter(in_use=True))["inUse"] == "true"
    assert to_query(FiberStrandFilter(in_use=False))["inUse"] == "false"


def test_region_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(FiberStrandFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(FiberStrandFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(FiberStrandCriticality)[:2]
    query = to_query(FiberStrandFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(FiberStrandFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(FiberStrandFilter(cost_center=""))

