"""Application wiring tests for the Asset and Finance domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.assetmgmt.app import app

PATHS = [
    "/api/v1/assets",
    "/api/v1/asset-tags",
    "/api/v1/purchase-orders",
    "/api/v1/invoices",
    "/api/v1/depreciation-schedules",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "assetmgmt"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["asset", "asset_tag", "purchase_order", "invoice", "depreciation_schedule"])


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
