"""Bearer-token authentication (Azure AD) and role checks."""
from __future__ import annotations

import os
from dataclasses import dataclass

from fastapi import Depends, Header

from itta_core.errors import ForbiddenError, UnauthorizedError


@dataclass(frozen=True)
class Principal:
    subject: str
    tenant: str
    roles: tuple[str, ...] = ()

    def has(self, role: str) -> bool:
        return role in self.roles


def _enforced() -> bool:
    return os.getenv("ITTA_AUTH_ENFORCED", "false").lower() == "true"


async def current_principal(
    authorization: str | None = Header(None),
    x_tenant_id: str | None = Header(None),
    x_actor: str | None = Header(None),
) -> Principal:
    tenant = x_tenant_id or os.getenv("ITTA_DEFAULT_TENANT", "uniti")
    if not _enforced():
        return Principal(subject=x_actor or "anonymous", tenant=tenant, roles=("inventory.read", "inventory.write"))
    if not authorization or not authorization.lower().startswith("bearer "):
        raise UnauthorizedError("missing bearer token")
    from itta_core.jwt import decode_token

    claims = decode_token(authorization.split(" ", 1)[1])
    roles = tuple(claims.get("roles", ()))
    return Principal(subject=str(claims.get("preferred_username", claims.get("sub"))), tenant=tenant, roles=roles)


def _require(role: str):
    async def dependency(principal: Principal = Depends(current_principal)) -> Principal:
        if not principal.has(role):
            raise ForbiddenError(f"role {role} required")
        return principal

    return dependency


require_read = _require("inventory.read")
require_write = _require("inventory.write")
