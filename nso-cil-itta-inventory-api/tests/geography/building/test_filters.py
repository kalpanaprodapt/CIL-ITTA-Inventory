"""Filter translation tests for Building."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.geography.building.filters import BuildingFilter, to_query
from itta_api.domains.geography.building.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(BuildingFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(BuildingFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(BuildingFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(BuildingFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(BuildingFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(BuildingFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(BuildingFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(BuildingFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(BuildingFilter(external_ref=""))


def test_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(BuildingFilter(site_id=value))["siteId"] == str(value)


def test_floors_range_is_sent():
    query = to_query(BuildingFilter(floors_min=1, floors_max=9))
    assert query["floorsMin"] == str(1)
    assert query["floorsMax"] == str(9)


def test_floors_zero_is_not_dropped():
    assert to_query(BuildingFilter(floors_min=0))["floorsMin"] == "0"


def test_year_built_range_is_sent():
    query = to_query(BuildingFilter(year_built_min=1, year_built_max=9))
    assert query["yearBuiltMin"] == str(1)
    assert query["yearBuiltMax"] == str(9)


def test_year_built_zero_is_not_dropped():
    assert to_query(BuildingFilter(year_built_min=0))["yearBuiltMin"] == "0"


def test_ownership_values_are_comma_joined():
    members = list(BuildingOwnership)[:2]
    query = to_query(BuildingFilter(ownership=members))
    assert query["ownership"] == ",".join(m.value for m in members)


def test_lease_expiry_window_is_iso_formatted():
    query = to_query(BuildingFilter(lease_expiry_from=date(2026, 1, 1), lease_expiry_to=date(2026, 1, 1)))
    assert query["leaseExpiryFrom"].startswith("2026-01-01")
    assert query["leaseExpiryTo"].startswith("2026-01-01")


def test_security_level_range_is_sent():
    query = to_query(BuildingFilter(security_level_min=1, security_level_max=9))
    assert query["securityLevelMin"] == str(1)
    assert query["securityLevelMax"] == str(9)


def test_security_level_zero_is_not_dropped():
    assert to_query(BuildingFilter(security_level_min=0))["securityLevelMin"] == "0"


def test_lifecycle_stage_values_are_comma_joined():
    members = list(BuildingLifecycleStage)[:2]
    query = to_query(BuildingFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(BuildingFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(BuildingFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(BuildingFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(BuildingFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(BuildingFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(BuildingFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(BuildingCriticality)[:2]
    query = to_query(BuildingFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

