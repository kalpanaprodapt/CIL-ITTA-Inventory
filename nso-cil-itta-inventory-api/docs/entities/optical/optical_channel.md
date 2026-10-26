# Optical Channel

Domain: **Optical** (`optical`) - ASGI app `itta_api.domains.optical.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `wavelength_nm` | `wavelengthNm` | Wavelength Nm | yes |
| `channel_number` | `channelNumber` | Channel Number | yes |
| `grid_type` | `gridType` | Grid Type | no |
| `bitrate_gbps` | `bitrateGbps` | Bitrate Gbps | no |
| `system_id` | `systemId` | System Id | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/optical-channels` search
* `GET /api/v1/optical-channels/meta` field catalogue
* `POST /api/v1/optical-channels/reconcile?apply=false` reconcile external records
* `GET /api/v1/optical-channels/export` CSV
