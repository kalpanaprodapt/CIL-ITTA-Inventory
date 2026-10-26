"""Filter translation tests for Roadm."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.roadm.filters import RoadmFilter, to_query
from itta_api.domains.optical.roadm.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RoadmFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RoadmFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RoadmFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RoadmFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RoadmFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RoadmFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RoadmFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RoadmFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RoadmFilter(external_ref=""))


def test_system_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RoadmFilter(system_id=value))["systemId"] == str(value)


def test_degrees_range_is_sent():
    query = to_query(RoadmFilter(degrees_min=1, degrees_max=9))
    assert query["degreesMin"] == str(1)
    assert query["degreesMax"] == str(9)


def test_degrees_zero_is_not_dropped():
    assert to_query(RoadmFilter(degrees_min=0))["degreesMin"] == "0"


def test_colorless_is_lowercase_boolean():
    assert to_query(RoadmFilter(colorless=True))["colorless"] == "true"
    assert to_query(RoadmFilter(colorless=False))["colorless"] == "false"


def test_directionless_is_lowercase_boolean():
    assert to_query(RoadmFilter(directionless=True))["directionless"] == "true"
    assert to_query(RoadmFilter(directionless=False))["directionless"] == "false"


def test_contentionless_is_lowercase_boolean():
    assert to_query(RoadmFilter(contentionless=True))["contentionless"] == "true"
    assert to_query(RoadmFilter(contentionless=False))["contentionless"] == "false"


def test_region_is_trimmed_into_query():
    assert to_query(RoadmFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(RoadmFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(RoadmFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(RoadmFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(RoadmCriticality)[:2]
    query = to_query(RoadmFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(RoadmFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(RoadmFilter(cost_center=""))

