# Change Request

Domain: **Change and Work Management** (`change`) - ASGI app `itta_api.domains.change.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `change_number` | `changeNumber` | Change Number | yes |
| `change_type` | `changeType` | Change Type | no |
| `risk_level` | `riskLevel` | Risk Level | no |
| `planned_start` | `plannedStart` | Planned Start | no |
| `planned_end` | `plannedEnd` | Planned End | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/change-requests` search
* `GET /api/v1/change-requests/meta` field catalogue
* `POST /api/v1/change-requests/reconcile?apply=false` reconcile external records
* `GET /api/v1/change-requests/export` CSV
