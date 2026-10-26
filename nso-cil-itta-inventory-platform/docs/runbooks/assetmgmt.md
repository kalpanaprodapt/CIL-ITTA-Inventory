# Runbook - Asset and Finance

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-assetmgmt-service | Spring Boot | 8080 | Grafana `itta-assetmgmt` |
| inventory-assetmgmt-api | FastAPI | 8000 | Grafana `itta-assetmgmt` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-assetmgmt-service inventory-assetmgmt-api
kubectl -n itta-inventory-prod logs deploy/inventory-assetmgmt-service --tail=200
curl -fsS https://api-assetmgmt.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Asset

* API: `/api/v1/assets` on `inventory-assetmgmt-service`, facade on `inventory-assetmgmt-api`
* Table: `inv_asset` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.assetmgmt.asset`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh assetmgmt Asset`

### Asset Tag

* API: `/api/v1/asset-tags` on `inventory-assetmgmt-service`, facade on `inventory-assetmgmt-api`
* Table: `inv_asset_tag` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.assetmgmt.assettag`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh assetmgmt AssetTag`

### Purchase Order

* API: `/api/v1/purchase-orders` on `inventory-assetmgmt-service`, facade on `inventory-assetmgmt-api`
* Table: `inv_purchase_order` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.assetmgmt.purchaseorder`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh assetmgmt PurchaseOrder`

### Invoice

* API: `/api/v1/invoices` on `inventory-assetmgmt-service`, facade on `inventory-assetmgmt-api`
* Table: `inv_invoice` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.assetmgmt.invoice`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh assetmgmt Invoice`

### Depreciation Schedule

* API: `/api/v1/depreciation-schedules` on `inventory-assetmgmt-service`, facade on `inventory-assetmgmt-api`
* Table: `inv_depreciation_schedule` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.assetmgmt.depreciationschedule`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh assetmgmt DepreciationSchedule`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-assetmgmt-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-assetmgmt-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
