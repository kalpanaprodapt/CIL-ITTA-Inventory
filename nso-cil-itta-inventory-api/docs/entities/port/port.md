# Port

Domain: **Port and Interface** (`port`) - ASGI app `itta_api.domains.port.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `device_id` | `deviceId` | Device Id | yes |
| `card_id` | `cardId` | Card Id | no |
| `port_number` | `portNumber` | Port Number | yes |
| `speed_mbps` | `speedMbps` | Speed Mbps | no |
| `media_type` | `mediaType` | Media Type | no |
| `admin_state` | `adminState` | Admin State | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/ports` search
* `GET /api/v1/ports/meta` field catalogue
* `POST /api/v1/ports/reconcile?apply=false` reconcile external records
* `GET /api/v1/ports/export` CSV
