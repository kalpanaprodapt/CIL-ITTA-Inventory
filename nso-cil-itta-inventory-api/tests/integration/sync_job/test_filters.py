"""Filter translation tests for Sync Job."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.sync_job.filters import SyncJobFilter, to_query
from itta_api.domains.integration.sync_job.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SyncJobFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SyncJobFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SyncJobFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SyncJobFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SyncJobFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SyncJobFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SyncJobFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SyncJobFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SyncJobFilter(external_ref=""))


def test_endpoint_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SyncJobFilter(endpoint_id=value))["endpointId"] == str(value)


def test_direction_values_are_comma_joined():
    members = list(SyncJobDirection)[:2]
    query = to_query(SyncJobFilter(direction=members))
    assert query["direction"] == ",".join(m.value for m in members)


def test_schedule_cron_is_trimmed_into_query():
    assert to_query(SyncJobFilter(schedule_cron="  abc ")) == {"scheduleCron": "abc"}


def test_schedule_cron_blank_is_omitted():
    assert "scheduleCron" not in to_query(SyncJobFilter(schedule_cron=""))


def test_last_run_at_window_is_iso_formatted():
    query = to_query(SyncJobFilter(last_run_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), last_run_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["lastRunAtFrom"].startswith("2026-01-01")
    assert query["lastRunAtTo"].startswith("2026-01-01")


def test_last_status_values_are_comma_joined():
    members = list(SyncJobLastStatus)[:2]
    query = to_query(SyncJobFilter(last_status=members))
    assert query["lastStatus"] == ",".join(m.value for m in members)


def test_owner_is_trimmed_into_query():
    assert to_query(SyncJobFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SyncJobFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SyncJobCriticality)[:2]
    query = to_query(SyncJobFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SyncJobFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SyncJobFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SyncJobFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")

