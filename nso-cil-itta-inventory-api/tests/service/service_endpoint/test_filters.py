"""Filter translation tests for Service Endpoint."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_endpoint.filters import ServiceEndpointFilter, to_query
from itta_api.domains.service.service_endpoint.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ServiceEndpointFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ServiceEndpointFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ServiceEndpointFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ServiceEndpointFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ServiceEndpointFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ServiceEndpointFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ServiceEndpointFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ServiceEndpointFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ServiceEndpointFilter(external_ref=""))


def test_service_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceEndpointFilter(service_id=value))["serviceId"] == str(value)


def test_site_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceEndpointFilter(site_id=value))["siteId"] == str(value)


def test_port_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceEndpointFilter(port_id=value))["portId"] == str(value)


def test_endpoint_role_values_are_comma_joined():
    members = list(ServiceEndpointEndpointRole)[:2]
    query = to_query(ServiceEndpointFilter(endpoint_role=members))
    assert query["endpointRole"] == ",".join(m.value for m in members)


def test_handoff_is_trimmed_into_query():
    assert to_query(ServiceEndpointFilter(handoff="  abc ")) == {"handoff": "abc"}


def test_handoff_blank_is_omitted():
    assert "handoff" not in to_query(ServiceEndpointFilter(handoff=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(ServiceEndpointFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ServiceEndpointFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ServiceEndpointFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ServiceEndpointFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ServiceEndpointLifecycleStage)[:2]
    query = to_query(ServiceEndpointFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)

