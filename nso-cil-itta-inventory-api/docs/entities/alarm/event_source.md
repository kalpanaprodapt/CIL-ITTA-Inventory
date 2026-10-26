# Event Source

Domain: **Alarms and Events** (`alarm`) - ASGI app `itta_api.domains.alarm.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `source_name` | `sourceName` | Source Name | yes |
| `protocol` | `protocol` | Protocol | no |
| `endpoint` | `endpoint` | Endpoint | no |
| `heartbeat_seconds` | `heartbeatSeconds` | Heartbeat Seconds | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/event-sources` search
* `GET /api/v1/event-sources/meta` field catalogue
* `POST /api/v1/event-sources/reconcile?apply=false` reconcile external records
* `GET /api/v1/event-sources/export` CSV
