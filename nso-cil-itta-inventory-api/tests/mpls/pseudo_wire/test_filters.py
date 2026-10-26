"""Filter translation tests for Pseudo Wire."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.pseudo_wire.filters import PseudoWireFilter, to_query
from itta_api.domains.mpls.pseudo_wire.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PseudoWireFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PseudoWireFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PseudoWireFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PseudoWireFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PseudoWireFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PseudoWireFilter(external_ref=""))


def test_vc_id_range_is_sent():
    query = to_query(PseudoWireFilter(vc_id_min=1, vc_id_max=9))
    assert query["vcIdMin"] == str(1)
    assert query["vcIdMax"] == str(9)


def test_vc_id_zero_is_not_dropped():
    assert to_query(PseudoWireFilter(vc_id_min=0))["vcIdMin"] == "0"


def test_a_end_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PseudoWireFilter(a_end_id=value))["aEndId"] == str(value)


def test_z_end_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(PseudoWireFilter(z_end_id=value))["zEndId"] == str(value)


def test_encapsulation_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(encapsulation="  abc ")) == {"encapsulation": "abc"}


def test_encapsulation_blank_is_omitted():
    assert "encapsulation" not in to_query(PseudoWireFilter(encapsulation=""))


def test_control_word_is_lowercase_boolean():
    assert to_query(PseudoWireFilter(control_word=True))["controlWord"] == "true"
    assert to_query(PseudoWireFilter(control_word=False))["controlWord"] == "false"


def test_cost_center_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PseudoWireFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(PseudoWireFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(PseudoWireFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(PseudoWireFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(PseudoWireFilter(labels=""))

