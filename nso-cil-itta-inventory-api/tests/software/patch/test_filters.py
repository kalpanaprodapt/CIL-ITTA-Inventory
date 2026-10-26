"""Filter translation tests for Patch."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.patch.filters import PatchFilter, to_query
from itta_api.domains.software.patch.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PatchFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PatchFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PatchFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PatchFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PatchFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PatchFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PatchFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PatchFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PatchFilter(external_ref=""))


def test_version_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PatchFilter(version_id=value))["versionId"] == str(value)


def test_patch_id_is_trimmed_into_query():
    assert to_query(PatchFilter(patch_id="  abc ")) == {"patchId": "abc"}


def test_patch_id_blank_is_omitted():
    assert "patchId" not in to_query(PatchFilter(patch_id=""))


def test_severity_values_are_comma_joined():
    members = list(PatchSeverity)[:2]
    query = to_query(PatchFilter(severity=members))
    assert query["severity"] == ",".join(m.value for m in members)


def test_released_on_window_is_iso_formatted():
    query = to_query(PatchFilter(released_on_from=date(2026, 1, 1), released_on_to=date(2026, 1, 1)))
    assert query["releasedOnFrom"].startswith("2026-01-01")
    assert query["releasedOnTo"].startswith("2026-01-01")


def test_reboot_required_is_lowercase_boolean():
    assert to_query(PatchFilter(reboot_required=True))["rebootRequired"] == "true"
    assert to_query(PatchFilter(reboot_required=False))["rebootRequired"] == "false"


def test_criticality_values_are_comma_joined():
    members = list(PatchCriticality)[:2]
    query = to_query(PatchFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(PatchFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PatchFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(PatchFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(PatchFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

