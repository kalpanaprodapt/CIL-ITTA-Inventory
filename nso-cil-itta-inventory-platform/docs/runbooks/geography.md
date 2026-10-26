# Runbook - Geography

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-geography-service | Spring Boot | 8080 | Grafana `itta-geography` |
| inventory-geography-api | FastAPI | 8000 | Grafana `itta-geography` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-geography-service inventory-geography-api
kubectl -n itta-inventory-prod logs deploy/inventory-geography-service --tail=200
curl -fsS https://api-geography.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Country

* API: `/api/v1/countries` on `inventory-geography-service`, facade on `inventory-geography-api`
* Table: `inv_country` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.geography.country`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh geography Country`

### Region

* API: `/api/v1/regions` on `inventory-geography-service`, facade on `inventory-geography-api`
* Table: `inv_region` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.geography.region`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh geography Region`

### City

* API: `/api/v1/cities` on `inventory-geography-service`, facade on `inventory-geography-api`
* Table: `inv_city` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.geography.city`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh geography City`

### Site

* API: `/api/v1/sites` on `inventory-geography-service`, facade on `inventory-geography-api`
* Table: `inv_site` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.geography.site`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh geography Site`

### Building

* API: `/api/v1/buildings` on `inventory-geography-service`, facade on `inventory-geography-api`
* Table: `inv_building` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.geography.building`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh geography Building`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-geography-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-geography-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
