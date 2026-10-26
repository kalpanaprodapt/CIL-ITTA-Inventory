"""Filter translation tests for Discovery Job."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_job.filters import DiscoveryJobFilter, to_query
from itta_api.domains.discovery.discovery_job.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(DiscoveryJobFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(DiscoveryJobFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(DiscoveryJobFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(DiscoveryJobFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(DiscoveryJobFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(DiscoveryJobFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(DiscoveryJobFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(DiscoveryJobFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(DiscoveryJobFilter(external_ref=""))


def test_profile_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(DiscoveryJobFilter(profile_id=value))["profileId"] == str(value)


def test_started_at_window_is_iso_formatted():
    query = to_query(DiscoveryJobFilter(started_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), started_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["startedAtFrom"].startswith("2026-01-01")
    assert query["startedAtTo"].startswith("2026-01-01")


def test_finished_at_window_is_iso_formatted():
    query = to_query(DiscoveryJobFilter(finished_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), finished_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["finishedAtFrom"].startswith("2026-01-01")
    assert query["finishedAtTo"].startswith("2026-01-01")


def test_job_state_values_are_comma_joined():
    members = list(DiscoveryJobJobState)[:2]
    query = to_query(DiscoveryJobFilter(job_state=members))
    assert query["jobState"] == ",".join(m.value for m in members)


def test_assets_found_range_is_sent():
    query = to_query(DiscoveryJobFilter(assets_found_min=1, assets_found_max=9))
    assert query["assetsFoundMin"] == str(1)
    assert query["assetsFoundMax"] == str(9)


def test_assets_found_zero_is_not_dropped():
    assert to_query(DiscoveryJobFilter(assets_found_min=0))["assetsFoundMin"] == "0"


def test_owner_is_trimmed_into_query():
    assert to_query(DiscoveryJobFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(DiscoveryJobFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(DiscoveryJobCriticality)[:2]
    query = to_query(DiscoveryJobFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(DiscoveryJobFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(DiscoveryJobFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(DiscoveryJobFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

