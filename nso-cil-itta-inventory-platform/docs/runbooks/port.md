# Runbook - Port and Interface

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-port-service | Spring Boot | 8080 | Grafana `itta-port` |
| inventory-port-api | FastAPI | 8000 | Grafana `itta-port` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-port-service inventory-port-api
kubectl -n itta-inventory-prod logs deploy/inventory-port-service --tail=200
curl -fsS https://api-port.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Port

* API: `/api/v1/ports` on `inventory-port-service`, facade on `inventory-port-api`
* Table: `inv_port` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.port.port`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh port Port`

### Interface

* API: `/api/v1/interfaces` on `inventory-port-service`, facade on `inventory-port-api`
* Table: `inv_interface` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.port.interface`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh port Interface`

### Sub Interface

* API: `/api/v1/sub-interfaces` on `inventory-port-service`, facade on `inventory-port-api`
* Table: `inv_sub_interface` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.port.subinterface`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh port SubInterface`

### Lag Group

* API: `/api/v1/lag-groups` on `inventory-port-service`, facade on `inventory-port-api`
* Table: `inv_lag_group` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.port.laggroup`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh port LagGroup`

### Transceiver

* API: `/api/v1/transceivers` on `inventory-port-service`, facade on `inventory-port-api`
* Table: `inv_transceiver` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.port.transceiver`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh port Transceiver`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-port-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-port-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
