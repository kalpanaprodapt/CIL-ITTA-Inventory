"""Application wiring tests for the MPLS and VPN domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.mpls.app import app

PATHS = [
    "/api/v1/mpls-lsps",
    "/api/v1/vpn-services",
    "/api/v1/pseudo-wires",
    "/api/v1/tunnel-endpoints",
    "/api/v1/qos-policies",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "mpls"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["mpls_lsp", "vpn_service", "pseudo_wire", "tunnel_endpoint", "qos_policy"])


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
