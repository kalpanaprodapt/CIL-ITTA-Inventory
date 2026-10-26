"""Resilient httpx wrapper for calls to the inventory system-of-record services."""
from __future__ import annotations

import asyncio
import logging
from typing import Any

import httpx

from itta_core.errors import ConflictError, NotFoundError, UnprocessableError, UpstreamError

log = logging.getLogger(__name__)
RETRYABLE = {502, 503, 504}


class UpstreamClient:
    def __init__(
        self,
        base_url: str,
        tenant: str,
        actor: str | None = None,
        timeout: float = 10.0,
        retries: int = 2,
        transport: httpx.AsyncBaseTransport | None = None,
    ) -> None:
        self._base = base_url.rstrip("/")
        self._tenant = tenant
        self._actor = actor
        self._timeout = timeout
        self._retries = retries
        self._transport = transport

    def _headers(self, extra: dict[str, str] | None = None) -> dict[str, str]:
        headers = {"X-Tenant-Id": self._tenant, "Accept": "application/json"}
        if self._actor:
            headers["X-Actor"] = self._actor
        if extra:
            headers.update(extra)
        return headers

    async def _request(self, method: str, path: str, **kwargs: Any) -> httpx.Response:
        attempt = 0
        while True:
            try:
                async with httpx.AsyncClient(
                    base_url=self._base, timeout=self._timeout, transport=self._transport
                ) as client:
                    response = await client.request(method, path, headers=self._headers(), **kwargs)
            except httpx.TransportError as exc:
                if attempt < self._retries:
                    attempt += 1
                    await asyncio.sleep(0.1 * 2**attempt)
                    continue
                raise UpstreamError(f"upstream unreachable: {exc}") from exc
            if response.status_code in RETRYABLE and attempt < self._retries:
                attempt += 1
                await asyncio.sleep(0.1 * 2**attempt)
                continue
            return self._check(response)

    @staticmethod
    def _check(response: httpx.Response) -> httpx.Response:
        code = response.status_code
        if code < 400:
            return response
        message = _message(response)
        if code == 404:
            raise NotFoundError(message)
        if code == 409:
            raise ConflictError(message)
        if code in (400, 422):
            raise UnprocessableError(message)
        raise UpstreamError(f"upstream returned {code}: {message}")

    async def get_json(self, path: str, params: dict[str, Any] | None = None) -> Any:
        return (await self._request("GET", path, params=params)).json()

    async def get_bytes(self, path: str, params: dict[str, Any] | None = None) -> bytes:
        return (await self._request("GET", path, params=params)).content

    async def post_json(self, path: str, body: Any) -> Any:
        return (await self._request("POST", path, json=body)).json()

    async def put_json(self, path: str, body: Any) -> Any:
        return (await self._request("PUT", path, json=body)).json()

    async def patch_json(self, path: str, params: dict[str, Any] | None = None, body: Any | None = None) -> Any:
        return (await self._request("PATCH", path, params=params, json=body)).json()

    async def delete(self, path: str) -> None:
        await self._request("DELETE", path)

    async def head(self, path: str) -> bool:
        try:
            await self._request("HEAD", path)
        except NotFoundError:
            return False
        return True


def _message(response: httpx.Response) -> str:
    try:
        body = response.json()
        if isinstance(body, dict) and "message" in body:
            return str(body["message"])
    except ValueError:
        pass
    return response.text[:200] or response.reason_phrase
