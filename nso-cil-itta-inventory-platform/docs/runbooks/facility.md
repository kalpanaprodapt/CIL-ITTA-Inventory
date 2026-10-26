# Runbook - Facility

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-facility-service | Spring Boot | 8080 | Grafana `itta-facility` |
| inventory-facility-api | FastAPI | 8000 | Grafana `itta-facility` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-facility-service inventory-facility-api
kubectl -n itta-inventory-prod logs deploy/inventory-facility-service --tail=200
curl -fsS https://api-facility.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Floor

* API: `/api/v1/floors` on `inventory-facility-service`, facade on `inventory-facility-api`
* Table: `inv_floor` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.facility.floor`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh facility Floor`

### Room

* API: `/api/v1/rooms` on `inventory-facility-service`, facade on `inventory-facility-api`
* Table: `inv_room` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.facility.room`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh facility Room`

### Rack

* API: `/api/v1/racks` on `inventory-facility-service`, facade on `inventory-facility-api`
* Table: `inv_rack` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.facility.rack`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh facility Rack`

### Rack Unit

* API: `/api/v1/rack-units` on `inventory-facility-service`, facade on `inventory-facility-api`
* Table: `inv_rack_unit` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.facility.rackunit`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh facility RackUnit`

### Power Feed

* API: `/api/v1/power-feeds` on `inventory-facility-service`, facade on `inventory-facility-api`
* Table: `inv_power_feed` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.facility.powerfeed`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh facility PowerFeed`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-facility-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-facility-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
