"""Service tests for Backhaul Link using an in-memory fake client."""
import asyncio
from uuid import UUID

import pytest

from itta_core.cache import AsyncTTLCache
from itta_core.errors import ConflictError, NotFoundError
from itta_core.models import LifecycleStatus
from itta_core.pagination import Page
from itta_api.domains.radio.backhaul_link.filters import BackhaulLinkFilter
from itta_api.domains.radio.backhaul_link.schemas import *  # noqa: F401,F403
from itta_api.domains.radio.backhaul_link.service import BackhaulLinkService
from tests.radio.backhaul_link.factories import ID, create_payload, raw_record, read_model


class FakeClient:
    def __init__(self):
        self.store = {ID: read_model()}
        self.calls = []

    async def get(self, id):
        self.calls.append(("get", id))
        if id not in self.store:
            raise NotFoundError("missing")
        return self.store[id]

    async def get_by_code(self, code):
        for item in self.store.values():
            if item.code == code:
                return item
        raise NotFoundError("missing")

    async def exists(self, code):
        return any(i.code == code for i in self.store.values())

    async def search(self, flt, page=0, size=20, sort=None):
        content = list(self.store.values())
        return Page[BackhaulLinkRead](content=content, page=page, size=size, total_elements=len(content), total_pages=1)

    async def summaries(self, status=None):
        return [BackhaulLinkSummary(id=i.id, code=i.code, name=i.name, status=i.status) for i in self.store.values()]

    async def create(self, payload):
        item = read_model(id=UUID(int=len(self.store) + 100), **payload.model_dump())
        self.store[item.id] = item
        return item

    async def update(self, id, payload):
        item = self.store[id].model_copy(update=payload.model_dump(exclude_unset=True, exclude={"expected_version"}))
        self.store[id] = item
        return item

    async def change_status(self, id, status):
        item = self.store[id].model_copy(update={"status": status})
        self.store[id] = item
        return item

    async def delete(self, id):
        self.store.pop(id)

    async def bulk_create(self, payloads):
        return BackhaulLinkBulkResult(succeeded=[await self.create(p) for p in payloads])

    async def status_stats(self):
        return {"ACTIVE": len(self.store)}


@pytest.fixture
def svc():
    client = FakeClient()
    return BackhaulLinkService(client, AsyncTTLCache()), client


def run(coro):
    return asyncio.run(coro)


def test_get_is_cached(svc):
    service, client = svc
    run(service.get(ID))
    run(service.get(ID))
    assert [c for c in client.calls if c[0] == "get"] == [("get", ID)]


def test_get_missing_raises(svc):
    service, _ = svc
    with pytest.raises(NotFoundError):
        run(service.get(UUID(int=5)))


def test_create_rejects_existing_code(svc):
    service, _ = svc
    with pytest.raises(ConflictError):
        run(service.create(create_payload()))


def test_create_new_code(svc):
    service, client = svc
    created = run(service.create(create_payload(code="NEW-1")))
    assert created.code == "NEW-1"
    assert created.id in client.store


def test_update_invalidates_cache(svc):
    service, client = svc
    run(service.get(ID))
    run(service.update(ID, BackhaulLinkUpdate(name="Renamed")))
    assert run(service.get(ID)).name == "Renamed"


def test_delete_removes(svc):
    service, client = svc
    run(service.delete(ID))
    assert ID not in client.store


def test_bulk_create_rejects_duplicate_codes(svc):
    service, _ = svc
    with pytest.raises(ConflictError):
        run(service.bulk_create([create_payload(code="A"), create_payload(code="A")]))


def test_summaries_are_cached(svc):
    service, _ = svc
    first = run(service.summaries())
    second = run(service.summaries())
    assert first == second


def test_export_csv_has_header_and_row(svc):
    service, _ = svc
    text = run(service.export_csv(BackhaulLinkFilter()))
    assert len(text.strip().splitlines()) == 2


def test_reconcile_dry_run_does_not_write(svc):
    service, client = svc
    items = run(service.reconcile([raw_record(name="Renamed")], apply=False))
    assert items[0].action.value == "update"
    assert client.store[ID].name != "Renamed"


def test_reconcile_apply_updates(svc):
    service, client = svc
    run(service.reconcile([raw_record(name="Renamed")], apply=True))
    assert client.store[ID].name == "Renamed"


def test_status_stats(svc):
    service, _ = svc
    assert run(service.stats()) == {"ACTIVE": 1}
