# Dns Record

Domain: **IP Management** (`ipam`) - ASGI app `itta_api.domains.ipam.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `zone_name` | `zoneName` | Zone Name | yes |
| `record_type` | `recordType` | Record Type | no |
| `record_value` | `recordValue` | Record Value | yes |
| `ttl` | `ttl` | Ttl | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/dns-records` search
* `GET /api/v1/dns-records/meta` field catalogue
* `POST /api/v1/dns-records/reconcile?apply=false` reconcile external records
* `GET /api/v1/dns-records/export` CSV
