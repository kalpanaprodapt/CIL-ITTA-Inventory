"""Filter translation tests for Software Image."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.software.software_image.filters import SoftwareImageFilter, to_query
from itta_api.domains.software.software_image.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SoftwareImageFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SoftwareImageFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SoftwareImageFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SoftwareImageFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SoftwareImageFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SoftwareImageFilter(external_ref=""))


def test_vendor_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(SoftwareImageFilter(vendor_id=value))["vendorId"] == str(value)


def test_image_name_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(image_name="  abc ")) == {"imageName": "abc"}


def test_image_name_blank_is_omitted():
    assert "imageName" not in to_query(SoftwareImageFilter(image_name=""))


def test_size_mb_range_is_sent():
    query = to_query(SoftwareImageFilter(size_mb_min=1, size_mb_max=9))
    assert query["sizeMbMin"] == str(1)
    assert query["sizeMbMax"] == str(9)


def test_size_mb_zero_is_not_dropped():
    assert to_query(SoftwareImageFilter(size_mb_min=0))["sizeMbMin"] == "0"


def test_checksum_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(checksum="  abc ")) == {"checksum": "abc"}


def test_checksum_blank_is_omitted():
    assert "checksum" not in to_query(SoftwareImageFilter(checksum=""))


def test_platform_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(platform="  abc ")) == {"platform": "abc"}


def test_platform_blank_is_omitted():
    assert "platform" not in to_query(SoftwareImageFilter(platform=""))


def test_storage_path_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(storage_path="  abc ")) == {"storagePath": "abc"}


def test_storage_path_blank_is_omitted():
    assert "storagePath" not in to_query(SoftwareImageFilter(storage_path=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(SoftwareImageLifecycleStage)[:2]
    query = to_query(SoftwareImageFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(SoftwareImageFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(SoftwareImageFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(SoftwareImageFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SoftwareImageFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SoftwareImageCriticality)[:2]
    query = to_query(SoftwareImageFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)

