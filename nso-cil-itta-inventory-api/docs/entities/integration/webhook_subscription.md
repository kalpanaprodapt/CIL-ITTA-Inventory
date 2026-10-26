# Webhook Subscription

Domain: **Integration Layer** (`integration`) - ASGI app `itta_api.domains.integration.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `callback_url` | `callbackUrl` | Callback Url | yes |
| `event_types` | `eventTypes` | Event Types | yes |
| `secret_ref` | `secretRef` | Secret Ref | no |
| `active` | `active` | Active | no |
| `retry_limit` | `retryLimit` | Retry Limit | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/webhook-subscriptions` search
* `GET /api/v1/webhook-subscriptions/meta` field catalogue
* `POST /api/v1/webhook-subscriptions/reconcile?apply=false` reconcile external records
* `GET /api/v1/webhook-subscriptions/export` CSV
