"""Filter translation tests for Hardware Model."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.vendor.hardware_model.filters import HardwareModelFilter, to_query
from itta_api.domains.vendor.hardware_model.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(HardwareModelFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(HardwareModelFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(HardwareModelFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(HardwareModelFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(HardwareModelFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(HardwareModelFilter(external_ref=""))


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(HardwareModelFilter(vendor_id=value))["vendorId"] == str(value)


def test_model_number_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(model_number="  abc ")) == {"modelNumber": "abc"}


def test_model_number_blank_is_omitted():
    assert "modelNumber" not in to_query(HardwareModelFilter(model_number=""))


def test_form_factor_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(form_factor="  abc ")) == {"formFactor": "abc"}


def test_form_factor_blank_is_omitted():
    assert "formFactor" not in to_query(HardwareModelFilter(form_factor=""))


def test_power_watts_range_is_sent():
    query = to_query(HardwareModelFilter(power_watts_min=1, power_watts_max=9))
    assert query["powerWattsMin"] == str(1)
    assert query["powerWattsMax"] == str(9)


def test_power_watts_zero_is_not_dropped():
    assert to_query(HardwareModelFilter(power_watts_min=0))["powerWattsMin"] == "0"


def test_eol_date_window_is_iso_formatted():
    query = to_query(HardwareModelFilter(eol_date_from=date(2026, 1, 1), eol_date_to=date(2026, 1, 1)))
    assert query["eolDateFrom"].startswith("2026-01-01")
    assert query["eolDateTo"].startswith("2026-01-01")


def test_eos_date_window_is_iso_formatted():
    query = to_query(HardwareModelFilter(eos_date_from=date(2026, 1, 1), eos_date_to=date(2026, 1, 1)))
    assert query["eosDateFrom"].startswith("2026-01-01")
    assert query["eosDateTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(HardwareModelFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(HardwareModelLifecycleStage)[:2]
    query = to_query(HardwareModelFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(HardwareModelFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(HardwareModelFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(HardwareModelFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(HardwareModelFilter(owner=""))

