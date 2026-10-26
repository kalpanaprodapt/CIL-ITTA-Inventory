# Runbook - Capacity Planning

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-capacity-service | Spring Boot | 8080 | Grafana `itta-capacity` |
| inventory-capacity-api | FastAPI | 8000 | Grafana `itta-capacity` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-capacity-service inventory-capacity-api
kubectl -n itta-inventory-prod logs deploy/inventory-capacity-service --tail=200
curl -fsS https://api-capacity.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Capacity Plan

* API: `/api/v1/capacity-plans` on `inventory-capacity-service`, facade on `inventory-capacity-api`
* Table: `inv_capacity_plan` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.capacity.capacityplan`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh capacity CapacityPlan`

### Utilization Sample

* API: `/api/v1/utilization-samples` on `inventory-capacity-service`, facade on `inventory-capacity-api`
* Table: `inv_utilization_sample` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.capacity.utilizationsample`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh capacity UtilizationSample`

### Threshold Policy

* API: `/api/v1/threshold-policies` on `inventory-capacity-service`, facade on `inventory-capacity-api`
* Table: `inv_threshold_policy` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.capacity.thresholdpolicy`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh capacity ThresholdPolicy`

### Forecast Model

* API: `/api/v1/forecast-models` on `inventory-capacity-service`, facade on `inventory-capacity-api`
* Table: `inv_forecast_model` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.capacity.forecastmodel`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh capacity ForecastModel`

### Reservation

* API: `/api/v1/reservations` on `inventory-capacity-service`, facade on `inventory-capacity-api`
* Table: `inv_reservation` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.capacity.reservation`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh capacity Reservation`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-capacity-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-capacity-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
