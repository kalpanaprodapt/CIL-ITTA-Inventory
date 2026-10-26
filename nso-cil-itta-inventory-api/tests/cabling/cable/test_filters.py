"""Filter translation tests for Cable."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.cable.filters import CableFilter, to_query
from itta_api.domains.cabling.cable.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CableFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CableFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CableFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CableFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CableFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CableFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CableFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CableFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CableFilter(external_ref=""))


def test_cable_type_values_are_comma_joined():
    members = list(CableCableType)[:2]
    query = to_query(CableFilter(cable_type=members))
    assert query["cableType"] == ",".join(m.value for m in members)


def test_length_m_range_is_sent():
    query = to_query(CableFilter(length_m_min=Decimal("1.5"), length_m_max=Decimal("9.5")))
    assert query["lengthMMin"] == str(Decimal("1.5"))
    assert query["lengthMMax"] == str(Decimal("9.5"))


def test_length_m_zero_is_not_dropped():
    assert to_query(CableFilter(length_m_min=0))["lengthMMin"] == "0"


def test_a_end_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CableFilter(a_end_port_id=value))["aEndPortId"] == str(value)


def test_z_end_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CableFilter(z_end_port_id=value))["zEndPortId"] == str(value)


def test_color_is_trimmed_into_query():
    assert to_query(CableFilter(color="  abc ")) == {"color": "abc"}


def test_color_blank_is_omitted():
    assert "color" not in to_query(CableFilter(color=""))


def test_label_is_trimmed_into_query():
    assert to_query(CableFilter(label="  abc ")) == {"label": "abc"}


def test_label_blank_is_omitted():
    assert "label" not in to_query(CableFilter(label=""))


def test_criticality_values_are_comma_joined():
    members = list(CableCriticality)[:2]
    query = to_query(CableFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(CableFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CableFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(CableFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(CableFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")

