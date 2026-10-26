"""Filter translation tests for Transceiver."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.port.transceiver.filters import TransceiverFilter, to_query
from itta_api.domains.port.transceiver.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TransceiverFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TransceiverFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TransceiverFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TransceiverFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TransceiverFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TransceiverFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TransceiverFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TransceiverFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TransceiverFilter(external_ref=""))


def test_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TransceiverFilter(port_id=value))["portId"] == str(value)


def test_form_factor_values_are_comma_joined():
    members = list(TransceiverFormFactor)[:2]
    query = to_query(TransceiverFilter(form_factor=members))
    assert query["formFactor"] == ",".join(m.value for m in members)


def test_wavelength_nm_range_is_sent():
    query = to_query(TransceiverFilter(wavelength_nm_min=1, wavelength_nm_max=9))
    assert query["wavelengthNmMin"] == str(1)
    assert query["wavelengthNmMax"] == str(9)


def test_wavelength_nm_zero_is_not_dropped():
    assert to_query(TransceiverFilter(wavelength_nm_min=0))["wavelengthNmMin"] == "0"


def test_reach_km_range_is_sent():
    query = to_query(TransceiverFilter(reach_km_min=Decimal("1.5"), reach_km_max=Decimal("9.5")))
    assert query["reachKmMin"] == str(Decimal("1.5"))
    assert query["reachKmMax"] == str(Decimal("9.5"))


def test_reach_km_zero_is_not_dropped():
    assert to_query(TransceiverFilter(reach_km_min=0))["reachKmMin"] == "0"


def test_serial_number_is_trimmed_into_query():
    assert to_query(TransceiverFilter(serial_number="  abc ")) == {"serialNumber": "abc"}


def test_serial_number_blank_is_omitted():
    assert "serialNumber" not in to_query(TransceiverFilter(serial_number=""))


def test_vendor_part_is_trimmed_into_query():
    assert to_query(TransceiverFilter(vendor_part="  abc ")) == {"vendorPart": "abc"}


def test_vendor_part_blank_is_omitted():
    assert "vendorPart" not in to_query(TransceiverFilter(vendor_part=""))


def test_labels_is_trimmed_into_query():
    assert to_query(TransceiverFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(TransceiverFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(TransceiverLifecycleStage)[:2]
    query = to_query(TransceiverFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(TransceiverFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(TransceiverFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(TransceiverFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(TransceiverFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(TransceiverFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(TransceiverFilter(owner=""))

