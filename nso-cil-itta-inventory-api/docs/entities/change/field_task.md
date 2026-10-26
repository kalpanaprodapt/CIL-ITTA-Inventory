# Field Task

Domain: **Change and Work Management** (`change`) - ASGI app `itta_api.domains.change.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `work_order_id` | `workOrderId` | Work Order Id | yes |
| `task_name` | `taskName` | Task Name | yes |
| `sequence` | `sequence` | Sequence | yes |
| `duration_minutes` | `durationMinutes` | Duration Minutes | no |
| `completed_on` | `completedOn` | Completed On | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/field-tasks` search
* `GET /api/v1/field-tasks/meta` field catalogue
* `POST /api/v1/field-tasks/reconcile?apply=false` reconcile external records
* `GET /api/v1/field-tasks/export` CSV
