"""Application wiring tests for the Virtual and Cloud domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.virtual.app import app

PATHS = [
    "/api/v1/vnfs",
    "/api/v1/hypervisors",
    "/api/v1/virtual-machines",
    "/api/v1/cloud-accounts",
    "/api/v1/k8s-clusters",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "virtual"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["vnf", "hypervisor", "virtual_machine", "cloud_account", "k8s_cluster"])


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
