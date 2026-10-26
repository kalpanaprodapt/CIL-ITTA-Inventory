"""Filter translation tests for Patch Port."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.patch_port.filters import PatchPortFilter, to_query
from itta_api.domains.cabling.patch_port.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PatchPortFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PatchPortFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PatchPortFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PatchPortFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PatchPortFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PatchPortFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PatchPortFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PatchPortFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PatchPortFilter(external_ref=""))


def test_panel_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PatchPortFilter(panel_id=value))["panelId"] == str(value)


def test_port_number_range_is_sent():
    query = to_query(PatchPortFilter(port_number_min=1, port_number_max=9))
    assert query["portNumberMin"] == str(1)
    assert query["portNumberMax"] == str(9)


def test_port_number_zero_is_not_dropped():
    assert to_query(PatchPortFilter(port_number_min=0))["portNumberMin"] == "0"


def test_connected_cable_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PatchPortFilter(connected_cable_id=value))["connectedCableId"] == str(value)


def test_polarity_is_trimmed_into_query():
    assert to_query(PatchPortFilter(polarity="  abc ")) == {"polarity": "abc"}


def test_polarity_blank_is_omitted():
    assert "polarity" not in to_query(PatchPortFilter(polarity=""))


def test_effective_to_window_is_iso_formatted():
    query = to_query(PatchPortFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(PatchPortFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(PatchPortFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(PatchPortLifecycleStage)[:2]
    query = to_query(PatchPortFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(PatchPortFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(PatchPortFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(PatchPortFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(PatchPortFilter(region=""))

