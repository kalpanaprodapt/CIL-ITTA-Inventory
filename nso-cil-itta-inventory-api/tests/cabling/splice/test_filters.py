"""Filter translation tests for Splice."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.cabling.splice.filters import SpliceFilter, to_query
from itta_api.domains.cabling.splice.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SpliceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SpliceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SpliceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SpliceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SpliceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SpliceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SpliceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SpliceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SpliceFilter(external_ref=""))


def test_enclosure_id_is_trimmed_into_query():
    assert to_query(SpliceFilter(enclosure_id="  abc ")) == {"enclosureId": "abc"}


def test_enclosure_id_blank_is_omitted():
    assert "enclosureId" not in to_query(SpliceFilter(enclosure_id=""))


def test_a_strand_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SpliceFilter(a_strand_id=value))["aStrandId"] == str(value)


def test_z_strand_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SpliceFilter(z_strand_id=value))["zStrandId"] == str(value)


def test_loss_db_range_is_sent():
    query = to_query(SpliceFilter(loss_db_min=Decimal("1.5"), loss_db_max=Decimal("9.5")))
    assert query["lossDbMin"] == str(Decimal("1.5"))
    assert query["lossDbMax"] == str(Decimal("9.5"))


def test_loss_db_zero_is_not_dropped():
    assert to_query(SpliceFilter(loss_db_min=0))["lossDbMin"] == "0"


def test_spliced_on_window_is_iso_formatted():
    query = to_query(SpliceFilter(spliced_on_from=date(2026, 1, 1), spliced_on_to=date(2026, 1, 1)))
    assert query["splicedOnFrom"].startswith("2026-01-01")
    assert query["splicedOnTo"].startswith("2026-01-01")


def test_lifecycle_stage_values_are_comma_joined():
    members = list(SpliceLifecycleStage)[:2]
    query = to_query(SpliceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(SpliceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(SpliceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(SpliceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SpliceFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(SpliceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SpliceFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SpliceCriticality)[:2]
    query = to_query(SpliceFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

