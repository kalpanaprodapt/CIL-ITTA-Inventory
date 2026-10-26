# Runbook - Change and Work Management

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-change-service | Spring Boot | 8080 | Grafana `itta-change` |
| inventory-change-api | FastAPI | 8000 | Grafana `itta-change` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-change-service inventory-change-api
kubectl -n itta-inventory-prod logs deploy/inventory-change-service --tail=200
curl -fsS https://api-change.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Change Request

* API: `/api/v1/change-requests` on `inventory-change-service`, facade on `inventory-change-api`
* Table: `inv_change_request` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.change.changerequest`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh change ChangeRequest`

### Maintenance Window

* API: `/api/v1/maintenance-windows` on `inventory-change-service`, facade on `inventory-change-api`
* Table: `inv_maintenance_window` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.change.maintenancewindow`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh change MaintenanceWindow`

### Work Order

* API: `/api/v1/work-orders` on `inventory-change-service`, facade on `inventory-change-api`
* Table: `inv_work_order` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.change.workorder`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh change WorkOrder`

### Field Task

* API: `/api/v1/field-tasks` on `inventory-change-service`, facade on `inventory-change-api`
* Table: `inv_field_task` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.change.fieldtask`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh change FieldTask`

### Technician

* API: `/api/v1/technicians` on `inventory-change-service`, facade on `inventory-change-api`
* Table: `inv_technician` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.change.technician`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh change Technician`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-change-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-change-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
