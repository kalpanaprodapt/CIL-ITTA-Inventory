"""Route tests for Alarm Instance."""
import pytest
from fastapi import FastAPI
from fastapi.testclient import TestClient

from itta_core.auth import Principal, require_read, require_write
from itta_core.errors import install_error_handlers
from itta_core.pagination import Page
from itta_api.domains.alarm.alarm_instance.router import get_service, router
from itta_api.domains.alarm.alarm_instance.schemas import *  # noqa: F401,F403
from tests.alarm.alarm_instance.factories import ID, create_payload, raw_record, read_model

BASE = "/api/v1/alarm-instances"


class StubService:
    async def search(self, flt, page, size, sort):
        return Page[AlarmInstanceRead](content=[read_model()], page=page, size=size, total_elements=1, total_pages=1)

    async def get(self, id):
        return read_model()

    async def get_by_code(self, code):
        return read_model()

    async def exists(self, code):
        return code == "ALARMI-0001"

    async def summaries(self, status):
        return []

    async def create(self, payload):
        return read_model()

    async def update(self, id, payload):
        return read_model(name="Renamed")

    async def change_status(self, id, status):
        return read_model(status=status)

    async def delete(self, id):
        return None

    async def bulk_create(self, payloads):
        return AlarmInstanceBulkResult(succeeded=[read_model()])

    async def stats(self):
        return {"ACTIVE": 1}

    async def export_csv(self, flt):
        return "code,name\nA,B\n"

    async def reconcile(self, records, apply):
        return []


@pytest.fixture
def client():
    app = FastAPI()
    install_error_handlers(app)
    app.include_router(router)
    app.dependency_overrides[get_service] = lambda: StubService()
    principal = Principal(subject="tester", tenant="tenant-a", roles=("inventory.read", "inventory.write"))
    app.dependency_overrides[require_read] = lambda: principal
    app.dependency_overrides[require_write] = lambda: principal
    return TestClient(app)


def test_meta_lists_fields(client):
    body = client.get(BASE + "/meta").json()
    assert body["entity"] == "AlarmInstance"
    assert any(f["name"] == "code" for f in body["fields"])


def test_search_returns_page(client):
    res = client.get(BASE, params={"q": "one"})
    assert res.status_code == 200
    assert res.json()["totalElements"] == 1


def test_get_by_id(client):
    assert client.get(f"{BASE}/{ID}").json()["code"] == "ALARMI-0001"


def test_create_returns_201_and_location(client):
    res = client.post(BASE, json=raw_record())
    assert res.status_code == 201
    assert res.headers["location"].endswith(str(ID))


def test_create_validation_error_is_422(client):
    record = raw_record()
    record.pop("code")
    assert client.post(BASE, json=record).status_code == 422


def test_update(client):
    res = client.put(f"{BASE}/{ID}", json={"name": "Renamed"})
    assert res.json()["name"] == "Renamed"


def test_delete_returns_204(client):
    assert client.delete(f"{BASE}/{ID}").status_code == 204


def test_head_exists(client):
    assert client.head(f"{BASE}/by-code/ALARMI-0001").status_code == 200
    assert client.head(f"{BASE}/by-code/NOPE").status_code == 404


def test_stats(client):
    assert client.get(BASE + "/stats/status").json() == {"ACTIVE": 1}


def test_export_is_csv(client):
    res = client.get(BASE + "/export")
    assert res.headers["content-type"].startswith("text/csv")


def test_change_status(client):
    res = client.patch(f"{BASE}/{ID}/status", params={"status": "MAINTENANCE"})
    assert res.json()["status"] == "MAINTENANCE"


def test_bulk(client):
    res = client.post(BASE + "/bulk", json=[raw_record()])
    assert len(res.json()["succeeded"]) == 1
