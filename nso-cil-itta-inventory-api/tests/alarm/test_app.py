"""Application wiring tests for the Alarms and Events domain."""
import pytest
from fastapi.testclient import TestClient

from itta_api.domains.alarm.app import app

PATHS = [
    "/api/v1/alarm-definitions",
    "/api/v1/alarm-instances",
    "/api/v1/event-sources",
    "/api/v1/suppression-rules",
    "/api/v1/ticket-links",
]


@pytest.fixture(scope="module")
def client():
    return TestClient(app)


def test_health(client):
    assert client.get("/healthz").json() == {"status": "ok", "domain": "alarm"}


def test_ready_lists_services(client):
    assert client.get("/readyz").json()["services"] == sorted(["alarm_definition", "alarm_instance", "event_source", "suppression_rule", "ticket_link"])


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
