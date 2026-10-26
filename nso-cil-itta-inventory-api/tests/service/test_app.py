"""Application wiring tests for the Services domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.service.app import app

PATHS = [
    "/api/v1/service-catalog-items",
    "/api/v1/service-instances",
    "/api/v1/service-orders",
    "/api/v1/service-dependencies",
    "/api/v1/service-endpoints",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "service"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["service_catalog_item", "service_instance", "service_order", "service_dependency", "service_endpoint"])


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
