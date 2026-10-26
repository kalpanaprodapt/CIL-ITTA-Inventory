"""Error types and FastAPI handlers."""
from __future__ import annotations

import logging

from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

log = logging.getLogger(__name__)


class ApiError(Exception):
    status_code = 500
    code = "internal_error"

    def __init__(self, message: str, details: list[str] | None = None) -> None:
        super().__init__(message)
        self.message = message
        self.details = details or []


class NotFoundError(ApiError):
    status_code = 404
    code = "not_found"


class ConflictError(ApiError):
    status_code = 409
    code = "conflict"


class UnprocessableError(ApiError):
    status_code = 422
    code = "unprocessable"


class UpstreamError(ApiError):
    status_code = 502
    code = "upstream_error"


class ForbiddenError(ApiError):
    status_code = 403
    code = "forbidden"


class UnauthorizedError(ApiError):
    status_code = 401
    code = "unauthorized"


def install_error_handlers(app: FastAPI) -> None:
    @app.exception_handler(ApiError)
    async def _api_error(request: Request, exc: ApiError) -> JSONResponse:
        body = {"code": exc.code, "message": exc.message, "details": exc.details, "path": request.url.path}
        return JSONResponse(status_code=exc.status_code, content=body)

    @app.exception_handler(Exception)
    async def _unexpected(request: Request, exc: Exception) -> JSONResponse:
        log.exception("unhandled error on %s", request.url.path)
        return JSONResponse(status_code=500, content={"code": "internal_error", "message": "Unexpected error"})
