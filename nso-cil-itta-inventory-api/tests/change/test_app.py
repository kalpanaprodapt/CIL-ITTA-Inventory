"""Application wiring tests for the Change and Work Management domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.change.app import app

PATHS = [
    "/api/v1/change-requests",
    "/api/v1/maintenance-windows",
    "/api/v1/work-orders",
    "/api/v1/field-tasks",
    "/api/v1/technicians",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "change"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["change_request", "maintenance_window", "work_order", "field_task", "technician"])


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
