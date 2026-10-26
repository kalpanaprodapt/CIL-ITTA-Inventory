# Interface

Domain: **Port and Interface** (`port`) - ASGI app `itta_api.domains.port.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `port_id` | `portId` | Port Id | yes |
| `if_name` | `ifName` | If Name | yes |
| `if_index` | `ifIndex` | If Index | no |
| `mac_address` | `macAddress` | Mac Address | no |
| `mtu` | `mtu` | Mtu | no |
| `oper_state` | `operState` | Oper State | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/interfaces` search
* `GET /api/v1/interfaces/meta` field catalogue
* `POST /api/v1/interfaces/reconcile?apply=false` reconcile external records
* `GET /api/v1/interfaces/export` CSV
