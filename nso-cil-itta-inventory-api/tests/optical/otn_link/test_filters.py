"""Filter translation tests for Otn Link."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.otn_link.filters import OtnLinkFilter, to_query
from itta_api.domains.optical.otn_link.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(OtnLinkFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(OtnLinkFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(OtnLinkFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(OtnLinkFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(OtnLinkFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(OtnLinkFilter(external_ref=""))


def test_a_node_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(OtnLinkFilter(a_node_id=value))["aNodeId"] == str(value)


def test_z_node_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(OtnLinkFilter(z_node_id=value))["zNodeId"] == str(value)


def test_odu_values_are_comma_joined():
    members = list(OtnLinkOdu)[:2]
    query = to_query(OtnLinkFilter(odu=members))
    assert query["odu"] == ",".join(m.value for m in members)


def test_latency_us_range_is_sent():
    query = to_query(OtnLinkFilter(latency_us_min=Decimal("1.5"), latency_us_max=Decimal("9.5")))
    assert query["latencyUsMin"] == str(Decimal("1.5"))
    assert query["latencyUsMax"] == str(Decimal("9.5"))


def test_latency_us_zero_is_not_dropped():
    assert to_query(OtnLinkFilter(latency_us_min=0))["latencyUsMin"] == "0"


def test_protection_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(protection="  abc ")) == {"protection": "abc"}


def test_protection_blank_is_omitted():
    assert "protection" not in to_query(OtnLinkFilter(protection=""))


def test_effective_to_window_is_iso_formatted():
    query = to_query(OtnLinkFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(OtnLinkFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(OtnLinkLifecycleStage)[:2]
    query = to_query(OtnLinkFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(OtnLinkFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(OtnLinkFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(OtnLinkFilter(region=""))

