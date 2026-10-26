"""Upstream client tests for Vlan using httpx.MockTransport."""
import asyncio

import httpx
import pytest

from itta_core.errors import NotFoundError, UpstreamError
from itta_core.http import UpstreamClient
from itta_api.domains.switching.vlan.client import VlanClient
from itta_api.domains.switching.vlan.filters import VlanFilter
from itta_api.domains.switching.vlan.schemas import *  # noqa: F401,F403
from tests.switching.vlan.factories import ID, create_payload, upstream_json


def make(handler) -> VlanClient:
    transport = httpx.MockTransport(handler)
    up = UpstreamClient("http://svc", tenant="tenant-a", transport=transport, retries=0)
    return VlanClient(up)


def run(coro):
    return asyncio.run(coro)


def test_get_parses_response():
    client = make(lambda req: httpx.Response(200, json=upstream_json()))
    assert run(client.get(ID)).id == ID


def test_get_404_maps_to_not_found():
    client = make(lambda req: httpx.Response(404, json={"message": "nope"}))
    with pytest.raises(NotFoundError):
        run(client.get(ID))


def test_5xx_maps_to_upstream_error():
    client = make(lambda req: httpx.Response(503, text="down"))
    with pytest.raises(UpstreamError):
        run(client.get(ID))


def test_search_sends_filter_params():
    seen = {}

    def handler(req):
        seen.update(dict(req.url.params))
        return httpx.Response(200, json={"content": [], "page": 0, "size": 20, "totalElements": 0, "totalPages": 0})

    run(make(handler).search(VlanFilter(q="core"), 0, 20, "code,asc"))
    assert seen["q"] == "core"
    assert seen["sort"] == "code,asc"


def test_create_posts_camel_case_body():
    seen = {}

    def handler(req):
        seen["body"] = req.content
        return httpx.Response(201, json=upstream_json())

    run(make(handler).create(create_payload()))
    assert b'"code"' in seen["body"]


def test_tenant_header_is_sent():
    seen = {}

    def handler(req):
        seen.update(req.headers)
        return httpx.Response(200, json=upstream_json())

    run(make(handler).get(ID))
    assert seen["x-tenant-id"] == "tenant-a"


def test_exists_uses_head():
    methods = []

    def handler(req):
        methods.append(req.method)
        return httpx.Response(200)

    assert run(make(handler).exists("X")) is True
    assert methods == ["HEAD"]
