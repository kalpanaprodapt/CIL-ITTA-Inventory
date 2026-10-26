# License Pool

Domain: **Software and Licensing** (`software`) - ASGI app `itta_api.domains.software.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vendor_id` | `vendorId` | Vendor Id | yes |
| `pool_name` | `poolName` | Pool Name | yes |
| `total_seats` | `totalSeats` | Total Seats | yes |
| `used_seats` | `usedSeats` | Used Seats | no |
| `renewal_date` | `renewalDate` | Renewal Date | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/license-pools` search
* `GET /api/v1/license-pools/meta` field catalogue
* `POST /api/v1/license-pools/reconcile?apply=false` reconcile external records
* `GET /api/v1/license-pools/export` CSV
