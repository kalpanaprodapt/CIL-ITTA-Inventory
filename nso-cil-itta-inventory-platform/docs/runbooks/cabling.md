# Runbook - Cabling

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-cabling-service | Spring Boot | 8080 | Grafana `itta-cabling` |
| inventory-cabling-api | FastAPI | 8000 | Grafana `itta-cabling` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-cabling-service inventory-cabling-api
kubectl -n itta-inventory-prod logs deploy/inventory-cabling-service --tail=200
curl -fsS https://api-cabling.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Cable

* API: `/api/v1/cables` on `inventory-cabling-service`, facade on `inventory-cabling-api`
* Table: `inv_cable` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.cabling.cable`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh cabling Cable`

### Patch Panel

* API: `/api/v1/patch-panels` on `inventory-cabling-service`, facade on `inventory-cabling-api`
* Table: `inv_patch_panel` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.cabling.patchpanel`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh cabling PatchPanel`

### Patch Port

* API: `/api/v1/patch-ports` on `inventory-cabling-service`, facade on `inventory-cabling-api`
* Table: `inv_patch_port` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.cabling.patchport`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh cabling PatchPort`

### Splice

* API: `/api/v1/splices` on `inventory-cabling-service`, facade on `inventory-cabling-api`
* Table: `inv_splice` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.cabling.splice`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh cabling Splice`

### Fiber Strand

* API: `/api/v1/fiber-strands` on `inventory-cabling-service`, facade on `inventory-cabling-api`
* Table: `inv_fiber_strand` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.cabling.fiberstrand`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh cabling FiberStrand`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-cabling-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-cabling-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
