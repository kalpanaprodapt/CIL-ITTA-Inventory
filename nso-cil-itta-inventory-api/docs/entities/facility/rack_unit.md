# Rack Unit

Domain: **Facility** (`facility`) - ASGI app `itta_api.domains.facility.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `rack_id` | `rackId` | Rack Id | yes |
| `unit_number` | `unitNumber` | Unit Number | yes |
| `occupied` | `occupied` | Occupied | no |
| `mounted_device_id` | `mountedDeviceId` | Mounted Device Id | no |
| `face` | `face` | Face | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/rack-units` search
* `GET /api/v1/rack-units/meta` field catalogue
* `POST /api/v1/rack-units/reconcile?apply=false` reconcile external records
* `GET /api/v1/rack-units/export` CSV
