"""Filter translation tests for Vrf Instance."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.ipam.vrf_instance.filters import VrfInstanceFilter, to_query
from itta_api.domains.ipam.vrf_instance.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(VrfInstanceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(VrfInstanceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(VrfInstanceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(VrfInstanceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(VrfInstanceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(VrfInstanceFilter(external_ref=""))


def test_vrf_name_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(vrf_name="  abc ")) == {"vrfName": "abc"}


def test_vrf_name_blank_is_omitted():
    assert "vrfName" not in to_query(VrfInstanceFilter(vrf_name=""))


def test_route_distinguisher_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(route_distinguisher="  abc ")) == {"routeDistinguisher": "abc"}


def test_route_distinguisher_blank_is_omitted():
    assert "routeDistinguisher" not in to_query(VrfInstanceFilter(route_distinguisher=""))


def test_import_targets_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(import_targets="  abc ")) == {"importTargets": "abc"}


def test_import_targets_blank_is_omitted():
    assert "importTargets" not in to_query(VrfInstanceFilter(import_targets=""))


def test_export_targets_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(export_targets="  abc ")) == {"exportTargets": "abc"}


def test_export_targets_blank_is_omitted():
    assert "exportTargets" not in to_query(VrfInstanceFilter(export_targets=""))


def test_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(VrfInstanceFilter(device_id=value))["deviceId"] == str(value)


def test_labels_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(VrfInstanceFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(VrfInstanceLifecycleStage)[:2]
    query = to_query(VrfInstanceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(VrfInstanceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(VrfInstanceFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(VrfInstanceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(VrfInstanceFilter(owner=""))

