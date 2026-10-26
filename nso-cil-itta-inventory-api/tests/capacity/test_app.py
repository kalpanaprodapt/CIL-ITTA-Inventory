"""Application wiring tests for the Capacity Planning domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.capacity.app import app

PATHS = [
    "/api/v1/capacity-plans",
    "/api/v1/utilization-samples",
    "/api/v1/threshold-policies",
    "/api/v1/forecast-models",
    "/api/v1/reservations",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "capacity"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["capacity_plan", "utilization_sample", "threshold_policy", "forecast_model", "reservation"])


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
