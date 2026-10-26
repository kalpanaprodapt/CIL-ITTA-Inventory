"""JWT decoding against the Azure AD JWKS endpoint."""
from __future__ import annotations

import os
from functools import lru_cache
from typing import Any

import jwt
from jwt import PyJWKClient

from itta_core.errors import UnauthorizedError


@lru_cache(maxsize=1)
def _jwks() -> PyJWKClient:
    tenant = os.environ["AZURE_AD_TENANT_ID"]
    return PyJWKClient(f"https://login.microsoftonline.com/{tenant}/discovery/v2.0/keys")


def decode_token(token: str) -> dict[str, Any]:
    try:
        key = _jwks().get_signing_key_from_jwt(token).key
        return jwt.decode(
            token,
            key,
            algorithms=["RS256"],
            audience=os.environ.get("AZURE_AD_AUDIENCE", "api://itta-inventory"),
        )
    except jwt.PyJWTError as exc:
        raise UnauthorizedError(f"invalid token: {exc}") from exc
