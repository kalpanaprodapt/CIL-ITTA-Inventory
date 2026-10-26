# Software Version

Domain: **Software and Licensing** (`software`) - ASGI app `itta_api.domains.software.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `image_id` | `imageId` | Image Id | yes |
| `version_string` | `versionString` | Version String | yes |
| `release_date` | `releaseDate` | Release Date | no |
| `end_of_support` | `endOfSupport` | End Of Support | no |
| `release_train` | `releaseTrain` | Release Train | no |
| `cve_count` | `cveCount` | Cve Count | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/software-versions` search
* `GET /api/v1/software-versions/meta` field catalogue
* `POST /api/v1/software-versions/reconcile?apply=false` reconcile external records
* `GET /api/v1/software-versions/export` CSV
