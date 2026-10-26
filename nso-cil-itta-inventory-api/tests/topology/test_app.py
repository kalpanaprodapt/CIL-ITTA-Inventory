"""Application wiring tests for the Topology and Links domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.topology.app import app

PATHS = [
    "/api/v1/links",
    "/api/v1/topology-nodes",
    "/api/v1/topology-edges",
    "/api/v1/topology-views",
    "/api/v1/path-traces",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "topology"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["link", "topology_node", "topology_edge", "topology_view", "path_trace"])


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
