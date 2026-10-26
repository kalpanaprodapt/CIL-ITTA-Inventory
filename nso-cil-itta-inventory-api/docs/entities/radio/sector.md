# Sector

Domain: **Radio and Mobile** (`radio`) - ASGI app `itta_api.domains.radio.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `base_station_id` | `baseStationId` | Base Station Id | yes |
| `sector_number` | `sectorNumber` | Sector Number | yes |
| `pci` | `pci` | Pci | no |
| `frequency_mhz` | `frequencyMhz` | Frequency Mhz | no |
| `bandwidth_mhz` | `bandwidthMhz` | Bandwidth Mhz | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/sectors` search
* `GET /api/v1/sectors/meta` field catalogue
* `POST /api/v1/sectors/reconcile?apply=false` reconcile external records
* `GET /api/v1/sectors/export` CSV
