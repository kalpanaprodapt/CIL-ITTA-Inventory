# Runbook - Integration Layer

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-integration-service | Spring Boot | 8080 | Grafana `itta-integration` |
| inventory-integration-api | FastAPI | 8000 | Grafana `itta-integration` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-integration-service inventory-integration-api
kubectl -n itta-inventory-prod logs deploy/inventory-integration-service --tail=200
curl -fsS https://api-integration.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Integration Endpoint

* API: `/api/v1/integration-endpoints` on `inventory-integration-service`, facade on `inventory-integration-api`
* Table: `inv_integration_endpoint` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.integration.integrationendpoint`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh integration IntegrationEndpoint`

### Sync Job

* API: `/api/v1/sync-jobs` on `inventory-integration-service`, facade on `inventory-integration-api`
* Table: `inv_sync_job` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.integration.syncjob`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh integration SyncJob`

### Sync Mapping

* API: `/api/v1/sync-mappings` on `inventory-integration-service`, facade on `inventory-integration-api`
* Table: `inv_sync_mapping` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.integration.syncmapping`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh integration SyncMapping`

### Import Batch

* API: `/api/v1/import-batchs` on `inventory-integration-service`, facade on `inventory-integration-api`
* Table: `inv_import_batch` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.integration.importbatch`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh integration ImportBatch`

### Webhook Subscription

* API: `/api/v1/webhook-subscriptions` on `inventory-integration-service`, facade on `inventory-integration-api`
* Table: `inv_webhook_subscription` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.integration.webhooksubscription`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh integration WebhookSubscription`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-integration-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-integration-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
