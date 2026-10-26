"""Filter translation tests for Maintenance Window."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.maintenance_window.filters import MaintenanceWindowFilter, to_query
from itta_api.domains.change.maintenance_window.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(MaintenanceWindowFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(MaintenanceWindowFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(MaintenanceWindowFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(MaintenanceWindowFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(MaintenanceWindowFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(MaintenanceWindowFilter(external_ref=""))


def test_window_name_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(window_name="  abc ")) == {"windowName": "abc"}


def test_window_name_blank_is_omitted():
    assert "windowName" not in to_query(MaintenanceWindowFilter(window_name=""))


def test_starts_at_window_is_iso_formatted():
    query = to_query(MaintenanceWindowFilter(starts_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), starts_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["startsAtFrom"].startswith("2026-01-01")
    assert query["startsAtTo"].startswith("2026-01-01")


def test_ends_at_window_is_iso_formatted():
    query = to_query(MaintenanceWindowFilter(ends_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), ends_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["endsAtFrom"].startswith("2026-01-01")
    assert query["endsAtTo"].startswith("2026-01-01")


def test_scope_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(scope="  abc ")) == {"scope": "abc"}


def test_scope_blank_is_omitted():
    assert "scope" not in to_query(MaintenanceWindowFilter(scope=""))


def test_impact_level_values_are_comma_joined():
    members = list(MaintenanceWindowImpactLevel)[:2]
    query = to_query(MaintenanceWindowFilter(impact_level=members))
    assert query["impactLevel"] == ",".join(m.value for m in members)


def test_effective_to_window_is_iso_formatted():
    query = to_query(MaintenanceWindowFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(MaintenanceWindowFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(MaintenanceWindowLifecycleStage)[:2]
    query = to_query(MaintenanceWindowFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(MaintenanceWindowFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(MaintenanceWindowFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(MaintenanceWindowFilter(region=""))

