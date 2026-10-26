"""Application wiring tests for the Vendor and Model domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.vendor.app import app

PATHS = [
    "/api/v1/vendors",
    "/api/v1/hardware-models",
    "/api/v1/skus",
    "/api/v1/support-contracts",
    "/api/v1/warranties",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "vendor"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["vendor", "hardware_model", "sku", "support_contract", "warranty"])


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
