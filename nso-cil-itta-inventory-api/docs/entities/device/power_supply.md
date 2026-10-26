# Power Supply

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
| `wattage` | `wattage` | Wattage | no |
| `feed_id` | `feedId` | Feed Id | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `efficiency` | `efficiency` | Efficiency | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/power-supplies` search
* `GET /api/v1/power-supplies/meta` field catalogue
* `POST /api/v1/power-supplies/reconcile?apply=false` reconcile external records
* `GET /api/v1/power-supplies/export` CSV
