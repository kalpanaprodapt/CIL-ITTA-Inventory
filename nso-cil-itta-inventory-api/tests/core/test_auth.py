import asyncio

import pytest

from itta_core import auth
from itta_core.errors import UnauthorizedError


def run(coro):
    return asyncio.run(coro)


def test_open_mode_grants_both_roles(monkeypatch):
    monkeypatch.setenv("ITTA_AUTH_ENFORCED", "false")
    principal = run(auth.current_principal(None, None, "alice"))
    assert principal.subject == "alice"
    assert principal.has("inventory.write")


def test_enforced_mode_requires_bearer(monkeypatch):
    monkeypatch.setenv("ITTA_AUTH_ENFORCED", "true")
    with pytest.raises(UnauthorizedError):
        run(auth.current_principal(None, None, None))


def test_principal_role_check():
    principal = auth.Principal(subject="x", tenant="t", roles=("inventory.read",))
    assert principal.has("inventory.read")
    assert not principal.has("inventory.write")
