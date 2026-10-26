"""FastAPI application factory shared by every domain service."""
from __future__ import annotations

import logging
import uuid
from collections.abc import Callable

from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from prometheus_client import CONTENT_TYPE_LATEST, Counter, Histogram, generate_latest
from starlette.responses import Response

from itta_core.cache import AsyncTTLCache
from itta_core.config import Settings
from itta_core.dependencies import ServiceRegistry
from itta_core.errors import install_error_handlers

REQUESTS = Counter("itta_api_requests_total", "HTTP requests", ["domain", "method", "status"])
LATENCY = Histogram("itta_api_request_seconds", "HTTP latency", ["domain", "method"])


def create_app(
    title: str,
    domain: str,
    upstream_env: str,
    registry_builder: Callable[[ServiceRegistry], None],
) -> FastAPI:
    settings = Settings.from_env(domain, upstream_env)
    logging.basicConfig(level=settings.log_level, format="%(asctime)s %(levelname)s %(name)s %(message)s")
    app = FastAPI(title=title, version="1.0.0")
    registry = ServiceRegistry()
    registry_builder(registry)
    app.state.settings = settings
    app.state.registry = registry
    app.state.cache = AsyncTTLCache(settings.cache_entries)
    install_error_handlers(app)
    if settings.cors_origins:
        app.add_middleware(CORSMiddleware, allow_origins=list(settings.cors_origins),
                           allow_methods=["*"], allow_headers=["*"])

    @app.middleware("http")
    async def correlation(request: Request, call_next):
        cid = request.headers.get("x-correlation-id", str(uuid.uuid4()))
        with LATENCY.labels(domain, request.method).time():
            response = await call_next(request)
        REQUESTS.labels(domain, request.method, str(response.status_code)).inc()
        response.headers["x-correlation-id"] = cid
        return response

    @app.get("/healthz", include_in_schema=False)
    async def healthz() -> dict[str, str]:
        return {"status": "ok", "domain": domain}

    @app.get("/readyz", include_in_schema=False)
    async def readyz() -> dict[str, object]:
        return {"status": "ready", "services": registry.names()}

    @app.get("/metrics", include_in_schema=False)
    async def metrics() -> Response:
        return Response(generate_latest(), media_type=CONTENT_TYPE_LATEST)

    return app
