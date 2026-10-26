"""Filter translation tests for Radio Unit."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.radio.radio_unit.filters import RadioUnitFilter, to_query
from itta_api.domains.radio.radio_unit.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(RadioUnitFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(RadioUnitFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(RadioUnitFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(RadioUnitFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(RadioUnitFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(RadioUnitFilter(external_ref=""))


def test_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(RadioUnitFilter(site_id=value))["siteId"] == str(value)


def test_unit_type_values_are_comma_joined():
    members = list(RadioUnitUnitType)[:2]
    query = to_query(RadioUnitFilter(unit_type=members))
    assert query["unitType"] == ",".join(m.value for m in members)


def test_band_mhz_range_is_sent():
    query = to_query(RadioUnitFilter(band_mhz_min=1, band_mhz_max=9))
    assert query["bandMhzMin"] == str(1)
    assert query["bandMhzMax"] == str(9)


def test_band_mhz_zero_is_not_dropped():
    assert to_query(RadioUnitFilter(band_mhz_min=0))["bandMhzMin"] == "0"


def test_tx_power_dbm_range_is_sent():
    query = to_query(RadioUnitFilter(tx_power_dbm_min=Decimal("1.5"), tx_power_dbm_max=Decimal("9.5")))
    assert query["txPowerDbmMin"] == str(Decimal("1.5"))
    assert query["txPowerDbmMax"] == str(Decimal("9.5"))


def test_tx_power_dbm_zero_is_not_dropped():
    assert to_query(RadioUnitFilter(tx_power_dbm_min=0))["txPowerDbmMin"] == "0"


def test_serial_number_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(RadioUnitFilter(serial_number=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(RadioUnitFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(RadioUnitFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(RadioUnitFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(RadioUnitLifecycleStage)[:2]
    query = to_query(RadioUnitFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(RadioUnitFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(RadioUnitFilter(source_system=""))

