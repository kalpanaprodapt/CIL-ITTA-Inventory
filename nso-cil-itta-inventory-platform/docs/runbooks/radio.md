# Runbook - Radio and Mobile

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-radio-service | Spring Boot | 8080 | Grafana `itta-radio` |
| inventory-radio-api | FastAPI | 8000 | Grafana `itta-radio` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-radio-service inventory-radio-api
kubectl -n itta-inventory-prod logs deploy/inventory-radio-service --tail=200
curl -fsS https://api-radio.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Radio Unit

* API: `/api/v1/radio-units` on `inventory-radio-service`, facade on `inventory-radio-api`
* Table: `inv_radio_unit` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.radio.radiounit`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh radio RadioUnit`

### Base Station

* API: `/api/v1/base-stations` on `inventory-radio-service`, facade on `inventory-radio-api`
* Table: `inv_base_station` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.radio.basestation`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh radio BaseStation`

### Antenna

* API: `/api/v1/antennas` on `inventory-radio-service`, facade on `inventory-radio-api`
* Table: `inv_antenna` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.radio.antenna`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh radio Antenna`

### Sector

* API: `/api/v1/sectors` on `inventory-radio-service`, facade on `inventory-radio-api`
* Table: `inv_sector` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.radio.sector`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh radio Sector`

### Backhaul Link

* API: `/api/v1/backhaul-links` on `inventory-radio-service`, facade on `inventory-radio-api`
* Table: `inv_backhaul_link` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.radio.backhaullink`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh radio BackhaulLink`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-radio-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-radio-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
