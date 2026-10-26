"""Tiny async TTL cache; swap for Redis through the same interface."""
from __future__ import annotations

import asyncio
import time
from typing import Any


class AsyncTTLCache:
    def __init__(self, max_entries: int = 10_000) -> None:
        self._data: dict[str, tuple[float, Any]] = {}
        self._lock = asyncio.Lock() if False else None
        self._max = max_entries

    async def get(self, key: str) -> Any | None:
        hit = self._data.get(key)
        if hit is None:
            return None
        expires, value = hit
        if expires < time.monotonic():
            self._data.pop(key, None)
            return None
        return value

    async def set(self, key: str, value: Any, ttl: float) -> None:
        if len(self._data) >= self._max:
            self._evict()
        self._data[key] = (time.monotonic() + ttl, value)

    async def delete(self, key: str) -> None:
        self._data.pop(key, None)

    async def invalidate_prefix(self, prefix: str) -> int:
        doomed = [k for k in self._data if k.startswith(prefix)]
        for k in doomed:
            del self._data[k]
        return len(doomed)

    def _evict(self) -> None:
        now = time.monotonic()
        for key in [k for k, (exp, _) in self._data.items() if exp < now]:
            del self._data[key]
        while len(self._data) >= self._max:
            self._data.pop(next(iter(self._data)))

    def __len__(self) -> int:
        return len(self._data)
