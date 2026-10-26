"""Filter translation tests for Tunnel Endpoint."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.mpls.tunnel_endpoint.filters import TunnelEndpointFilter, to_query
from itta_api.domains.mpls.tunnel_endpoint.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TunnelEndpointFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TunnelEndpointFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TunnelEndpointFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TunnelEndpointFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TunnelEndpointFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TunnelEndpointFilter(external_ref=""))


def test_device_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TunnelEndpointFilter(device_id=value))["deviceId"] == str(value)


def test_tunnel_name_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(tunnel_name="  abc ")) == {"tunnelName": "abc"}


def test_tunnel_name_blank_is_omitted():
    assert "tunnelName" not in to_query(TunnelEndpointFilter(tunnel_name=""))


def test_tunnel_type_values_are_comma_joined():
    members = list(TunnelEndpointTunnelType)[:2]
    query = to_query(TunnelEndpointFilter(tunnel_type=members))
    assert query["tunnelType"] == ",".join(m.value for m in members)


def test_source_ip_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(source_ip="  abc ")) == {"sourceIp": "abc"}


def test_source_ip_blank_is_omitted():
    assert "sourceIp" not in to_query(TunnelEndpointFilter(source_ip=""))


def test_destination_ip_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(destination_ip="  abc ")) == {"destinationIp": "abc"}


def test_destination_ip_blank_is_omitted():
    assert "destinationIp" not in to_query(TunnelEndpointFilter(destination_ip=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(TunnelEndpointFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(TunnelEndpointFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(TunnelEndpointFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(TunnelEndpointLifecycleStage)[:2]
    query = to_query(TunnelEndpointFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(TunnelEndpointFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(TunnelEndpointFilter(source_system=""))

