"""Scheduled job: pulls records from the integration sources and reconciles one domain.

Run by the `<workload>-sync` Kubernetes CronJob: ``python -m itta_core.jobs.reconcile_sync --domain device``.
"""
from __future__ import annotations

import argparse
import asyncio
import json
import logging
import os
import sys

import httpx

log = logging.getLogger("itta.sync")


def parse_args(argv: list[str] | None = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Reconcile one inventory domain with its sources")
    parser.add_argument("--domain", required=True)
    parser.add_argument("--page-size", type=int, default=500)
    parser.add_argument("--apply", action="store_true", help="apply the plan instead of a dry run")
    return parser.parse_args(argv)


async def fetch_source_records(client: httpx.AsyncClient, domain: str, page_size: int) -> list[dict[str, object]]:
    """Read records from the staging area of the integration layer (ImportBatch rows)."""
    response = await client.get(f"/api/v1/import-batches?size={page_size}&batchState=DONE")
    response.raise_for_status()
    return response.json().get("content", [])


async def run(domain: str, page_size: int, apply: bool) -> int:
    base = os.environ.get("ITTA_SELF_URL", "http://localhost:8000")
    headers = {"X-Tenant-Id": os.getenv("ITTA_DEFAULT_TENANT", "uniti")}
    async with httpx.AsyncClient(base_url=base, timeout=30.0, headers=headers) as client:
        records = await fetch_source_records(client, domain, page_size)
        log.info("domain=%s fetched=%d apply=%s", domain, len(records), apply)
        summary = {"domain": domain, "fetched": len(records), "applied": apply, "actions": {}}
        if records:
            response = await client.post(
                f"/api/v1/{domain}/reconcile", params={"apply": str(apply).lower()}, json=records
            )
            response.raise_for_status()
            for item in response.json():
                summary["actions"][item["action"]] = summary["actions"].get(item["action"], 0) + 1
        print(json.dumps(summary, sort_keys=True))
        return 0


def main(argv: list[str] | None = None) -> int:
    logging.basicConfig(level=os.getenv("ITTA_LOG_LEVEL", "INFO"))
    args = parse_args(argv)
    return asyncio.run(run(args.domain, args.page_size, args.apply))


if __name__ == "__main__":
    sys.exit(main())
