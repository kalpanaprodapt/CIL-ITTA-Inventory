"""Runtime settings resolved from environment variables."""
from __future__ import annotations

import os
from dataclasses import dataclass, field


@dataclass(frozen=True)
class Settings:
    domain: str
    upstream_url: str
    default_tenant: str = "uniti"
    upstream_timeout: float = 10.0
    upstream_retries: int = 2
    cache_entries: int = 10_000
    log_level: str = "INFO"
    cors_origins: tuple[str, ...] = field(default_factory=tuple)

    @classmethod
    def from_env(cls, domain: str, upstream_env: str) -> "Settings":
        origins = tuple(o for o in os.getenv("ITTA_CORS_ORIGINS", "").split(",") if o)
        return cls(
            domain=domain,
            upstream_url=os.getenv(upstream_env, f"http://inventory-{domain}-service:8080"),
            default_tenant=os.getenv("ITTA_DEFAULT_TENANT", "uniti"),
            upstream_timeout=float(os.getenv("ITTA_UPSTREAM_TIMEOUT", "10")),
            upstream_retries=int(os.getenv("ITTA_UPSTREAM_RETRIES", "2")),
            cache_entries=int(os.getenv("ITTA_CACHE_ENTRIES", "10000")),
            log_level=os.getenv("ITTA_LOG_LEVEL", "INFO"),
            cors_origins=origins,
        )
