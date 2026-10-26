# Fan Tray

Domain: **Network Device** (`device`) - ASGI app `itta_api.domains.device.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `chassis_id` | `chassisId` | Chassis Id | yes |
| `slot_number` | `slotNumber` | Slot Number | no |
| `fan_count` | `fanCount` | Fan Count | no |
| `airflow` | `airflow` | Airflow | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/fan-traies` search
* `GET /api/v1/fan-traies/meta` field catalogue
* `POST /api/v1/fan-traies/reconcile?apply=false` reconcile external records
* `GET /api/v1/fan-traies/export` CSV
