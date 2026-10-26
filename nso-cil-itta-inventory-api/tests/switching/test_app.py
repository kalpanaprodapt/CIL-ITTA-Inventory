"""Application wiring tests for the L2 and L3 Routing domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.switching.app import app

PATHS = [
    "/api/v1/vlans",
    "/api/v1/vlan-pools",
    "/api/v1/route-targets",
    "/api/v1/bgp-sessions",
    "/api/v1/ospf-areas",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "switching"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["vlan", "vlan_pool", "route_target", "bgp_session", "ospf_area"])


def test_metrics_endpoint_exposes_prometheus(client):
    body = client.get("/metrics").text
    assert "itta_api_requests_total" in body


@pytest.mark.parametrize("path", PATHS)
def test_routes_are_registered(client, path):
    schema = client.get("/openapi.json").json()
    assert path in schema["paths"]


def test_correlation_id_is_echoed(client):
    res = client.get("/healthz", headers={"x-correlation-id": "abc-123"})
    assert res.headers["x-correlation-id"] == "abc-123"
