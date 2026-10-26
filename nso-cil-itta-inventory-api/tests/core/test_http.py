import asyncio

import httpx
import pytest

from itta_core.errors import ConflictError, NotFoundError, UnprocessableError, UpstreamError
from itta_core.http import UpstreamClient


def client(handler, retries=0):
    return UpstreamClient("http://svc", tenant="t", actor="me", transport=httpx.MockTransport(handler), retries=retries)


def run(coro):
    return asyncio.run(coro)


def test_headers_include_tenant_and_actor():
    seen = {}

    def handler(req):
        seen.update(req.headers)
        return httpx.Response(200, json={})

    run(client(handler).get_json("/x"))
    assert seen["x-tenant-id"] == "t"
    assert seen["x-actor"] == "me"


@pytest.mark.parametrize("status,exc", [(404, NotFoundError), (409, ConflictError), (400, UnprocessableError), (422, UnprocessableError), (500, UpstreamError)])
def test_status_mapping(status, exc):
    with pytest.raises(exc):
        run(client(lambda r: httpx.Response(status, json={"message": "m"})).get_json("/x"))


def test_retries_on_503_then_succeeds():
    calls = []

    def handler(req):
        calls.append(1)
        return httpx.Response(503) if len(calls) < 2 else httpx.Response(200, json={"ok": True})

    assert run(client(handler, retries=2).get_json("/x")) == {"ok": True}
    assert len(calls) == 2


def test_head_returns_false_on_404():
    assert run(client(lambda r: httpx.Response(404)).head("/x")) is False


def test_transport_errors_become_upstream_errors():
    def handler(req):
        raise httpx.ConnectError("boom")

    with pytest.raises(UpstreamError):
        run(client(handler).get_json("/x"))
