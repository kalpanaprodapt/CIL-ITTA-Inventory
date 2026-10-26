# Software Image

Domain: **Software and Licensing** (`software`) - ASGI app `itta_api.domains.software.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vendor_id` | `vendorId` | Vendor Id | yes |
| `image_name` | `imageName` | Image Name | yes |
| `size_mb` | `sizeMb` | Size Mb | no |
| `checksum` | `checksum` | Checksum | no |
| `platform` | `platform` | Platform | no |
| `storage_path` | `storagePath` | Storage Path | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/software-images` search
* `GET /api/v1/software-images/meta` field catalogue
* `POST /api/v1/software-images/reconcile?apply=false` reconcile external records
* `GET /api/v1/software-images/export` CSV
