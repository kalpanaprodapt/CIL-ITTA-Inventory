# Line Card

Domain: **Network Device** (`device`) - ASGI app `itta_api.domains.device.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `chassis_id` | `chassisId` | Chassis Id | yes |
| `slot_number` | `slotNumber` | Slot Number | yes |
| `card_type` | `cardType` | Card Type | no |
| `port_count` | `portCount` | Port Count | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `firmware` | `firmware` | Firmware | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/line-cards` search
* `GET /api/v1/line-cards/meta` field catalogue
* `POST /api/v1/line-cards/reconcile?apply=false` reconcile external records
* `GET /api/v1/line-cards/export` CSV
