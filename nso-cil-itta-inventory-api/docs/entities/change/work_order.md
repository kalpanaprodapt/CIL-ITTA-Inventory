# Work Order

Domain: **Change and Work Management** (`change`) - ASGI app `itta_api.domains.change.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `work_order_number` | `workOrderNumber` | Work Order Number | yes |
| `work_type` | `workType` | Work Type | yes |
| `assigned_technician_id` | `assignedTechnicianId` | Assigned Technician Id | no |
| `due_date` | `dueDate` | Due Date | no |
| `work_state` | `workState` | Work State | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/work-orders` search
* `GET /api/v1/work-orders/meta` field catalogue
* `POST /api/v1/work-orders/reconcile?apply=false` reconcile external records
* `GET /api/v1/work-orders/export` CSV
