"""Service registry and FastAPI dependency providers."""
from __future__ import annotations

from collections.abc import Callable
from typing import Any

from fastapi import Depends, Request

from itta_core.auth import Principal, current_principal
from itta_core.cache import AsyncTTLCache
from itta_core.http import UpstreamClient


class ServiceRegistry:
    def __init__(self) -> None:
        self._factories: dict[str, Callable[[UpstreamClient, AsyncTTLCache], Any]] = {}

    def register(self, name: str, factory: Callable[[UpstreamClient, AsyncTTLCache], Any]) -> None:
        self._factories[name] = factory

    def build(self, name: str, upstream: UpstreamClient, cache: AsyncTTLCache) -> Any:
        return self._factories[name](upstream, cache)

    def names(self) -> list[str]:
        return sorted(self._factories)


def service_provider(name: str):
    def provider(request: Request, principal: Principal = Depends(current_principal)) -> Any:
        settings = request.app.state.settings
        upstream = UpstreamClient(
            settings.upstream_url,
            tenant=principal.tenant,
            actor=principal.subject,
            timeout=settings.upstream_timeout,
            retries=settings.upstream_retries,
        )
        return request.app.state.registry.build(name, upstream, request.app.state.cache)

    return provider
