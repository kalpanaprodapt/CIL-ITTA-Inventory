# Alarm Definition

Domain: **Alarms and Events** (`alarm`) - ASGI app `itta_api.domains.alarm.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `alarm_code` | `alarmCode` | Alarm Code | yes |
| `severity` | `severity` | Severity | no |
| `probable_cause` | `probableCause` | Probable Cause | no |
| `auto_clear` | `autoClear` | Auto Clear | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/alarm-definitions` search
* `GET /api/v1/alarm-definitions/meta` field catalogue
* `POST /api/v1/alarm-definitions/reconcile?apply=false` reconcile external records
* `GET /api/v1/alarm-definitions/export` CSV
