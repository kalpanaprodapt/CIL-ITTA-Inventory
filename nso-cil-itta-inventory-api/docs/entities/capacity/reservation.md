# Reservation

Domain: **Capacity Planning** (`capacity`) - ASGI app `itta_api.domains.capacity.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `resource_ref` | `resourceRef` | Resource Ref | yes |
| `reserved_for` | `reservedFor` | Reserved For | yes |
| `quantity` | `quantity` | Quantity | yes |
| `start_date` | `startDate` | Start Date | yes |
| `end_date` | `endDate` | End Date | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/reservations` search
* `GET /api/v1/reservations/meta` field catalogue
* `POST /api/v1/reservations/reconcile?apply=false` reconcile external records
* `GET /api/v1/reservations/export` CSV
