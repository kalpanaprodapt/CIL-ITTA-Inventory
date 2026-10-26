"""Application wiring tests for the Radio and Mobile domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.radio.app import app

PATHS = [
    "/api/v1/radio-units",
    "/api/v1/base-stations",
    "/api/v1/antennas",
    "/api/v1/sectors",
    "/api/v1/backhaul-links",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "radio"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["radio_unit", "base_station", "antenna", "sector", "backhaul_link"])


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
