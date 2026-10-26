"""Application wiring tests for the Cabling domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.cabling.app import app

PATHS = [
    "/api/v1/cables",
    "/api/v1/patch-panels",
    "/api/v1/patch-ports",
    "/api/v1/splices",
    "/api/v1/fiber-strands",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "cabling"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["cable", "patch_panel", "patch_port", "splice", "fiber_strand"])


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
