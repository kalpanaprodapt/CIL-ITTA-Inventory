"""Application wiring tests for the IP Management domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.ipam.app import app

PATHS = [
    "/api/v1/ip-subnets",
    "/api/v1/ip-addresses",
    "/api/v1/ip-pools",
    "/api/v1/vrf-instances",
    "/api/v1/dns-records",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "ipam"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["ip_subnet", "ip_address", "ip_pool", "vrf_instance", "dns_record"])


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
