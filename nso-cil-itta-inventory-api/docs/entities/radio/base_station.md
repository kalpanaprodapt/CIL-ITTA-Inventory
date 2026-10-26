# Base Station

Domain: **Radio and Mobile** (`radio`) - ASGI app `itta_api.domains.radio.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `site_id` | `siteId` | Site Id | yes |
| `enb_id` | `enbId` | Enb Id | yes |
| `technology` | `technology` | Technology | no |
| `sector_count` | `sectorCount` | Sector Count | no |
| `cell_count` | `cellCount` | Cell Count | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/base-stations` search
* `GET /api/v1/base-stations/meta` field catalogue
* `POST /api/v1/base-stations/reconcile?apply=false` reconcile external records
* `GET /api/v1/base-stations/export` CSV
