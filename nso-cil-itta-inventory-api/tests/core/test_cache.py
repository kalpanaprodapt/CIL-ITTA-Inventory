import asyncio

from itta_core.cache import AsyncTTLCache


def run(coro):
    return asyncio.run(coro)


def test_set_get_roundtrip():
    cache = AsyncTTLCache()
    run(cache.set("a", 1, 10))
    assert run(cache.get("a")) == 1


def test_expired_entries_are_dropped():
    cache = AsyncTTLCache()
    run(cache.set("a", 1, -1))
    assert run(cache.get("a")) is None
    assert len(cache) == 0


def test_invalidate_prefix_removes_matching_keys_only():
    cache = AsyncTTLCache()
    run(cache.set("site:1", 1, 10))
    run(cache.set("site:2", 2, 10))
    run(cache.set("rack:1", 3, 10))
    assert run(cache.invalidate_prefix("site:")) == 2
    assert run(cache.get("rack:1")) == 3


def test_eviction_keeps_cache_bounded():
    cache = AsyncTTLCache(max_entries=3)
    for i in range(10):
        run(cache.set(str(i), i, 10))
    assert len(cache) <= 3
