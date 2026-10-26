"""Filter translation tests for Patch Panel."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.patch_panel.filters import PatchPanelFilter, to_query
from itta_api.domains.cabling.patch_panel.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PatchPanelFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PatchPanelFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PatchPanelFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PatchPanelFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PatchPanelFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PatchPanelFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PatchPanelFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PatchPanelFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PatchPanelFilter(external_ref=""))


def test_room_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PatchPanelFilter(room_id=value))["roomId"] == str(value)


def test_rack_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PatchPanelFilter(rack_id=value))["rackId"] == str(value)


def test_panel_type_values_are_comma_joined():
    members = list(PatchPanelPanelType)[:2]
    query = to_query(PatchPanelFilter(panel_type=members))
    assert query["panelType"] == ",".join(m.value for m in members)


def test_port_count_range_is_sent():
    query = to_query(PatchPanelFilter(port_count_min=1, port_count_max=9))
    assert query["portCountMin"] == str(1)
    assert query["portCountMax"] == str(9)


def test_port_count_zero_is_not_dropped():
    assert to_query(PatchPanelFilter(port_count_min=0))["portCountMin"] == "0"


def test_label_is_trimmed_into_query():
    assert to_query(PatchPanelFilter(label="  abc ")) == {"label": "abc"}


def test_label_blank_is_omitted():
    assert "label" not in to_query(PatchPanelFilter(label=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(PatchPanelFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(PatchPanelFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(PatchPanelFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(PatchPanelFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(PatchPanelLifecycleStage)[:2]
    query = to_query(PatchPanelFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

