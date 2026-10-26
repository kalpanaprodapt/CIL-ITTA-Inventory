# Antenna

Domain: **Radio and Mobile** (`radio`) - ASGI app `itta_api.domains.radio.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `sector_id` | `sectorId` | Sector Id | yes |
| `model` | `model` | Model | no |
| `height_m` | `heightM` | Height M | no |
| `azimuth_deg` | `azimuthDeg` | Azimuth Deg | no |
| `tilt_deg` | `tiltDeg` | Tilt Deg | no |
| `gain_dbi` | `gainDbi` | Gain Dbi | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/antennas` search
* `GET /api/v1/antennas/meta` field catalogue
* `POST /api/v1/antennas/reconcile?apply=false` reconcile external records
* `GET /api/v1/antennas/export` CSV
