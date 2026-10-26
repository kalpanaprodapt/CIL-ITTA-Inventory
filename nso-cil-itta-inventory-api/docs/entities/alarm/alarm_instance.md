# Alarm Instance

Domain: **Alarms and Events** (`alarm`) - ASGI app `itta_api.domains.alarm.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `definition_id` | `definitionId` | Definition Id | yes |
| `source_ref` | `sourceRef` | Source Ref | yes |
| `raised_at` | `raisedAt` | Raised At | yes |
| `cleared_at` | `clearedAt` | Cleared At | no |
| `acked_by` | `ackedBy` | Acked By | no |
| `alarm_state` | `alarmState` | Alarm State | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/alarm-instances` search
* `GET /api/v1/alarm-instances/meta` field catalogue
* `POST /api/v1/alarm-instances/reconcile?apply=false` reconcile external records
* `GET /api/v1/alarm-instances/export` CSV
