"""Application wiring tests for the Network Device domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.device.app import app

PATHS = [
    "/api/v1/network-devices",
    "/api/v1/chassises",
    "/api/v1/line-cards",
    "/api/v1/power-supplies",
    "/api/v1/fan-traies",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "device"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["network_device", "chassis", "line_card", "power_supply", "fan_tray"])


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
