# Asset Tag

Domain: **Asset and Finance** (`assetmgmt`) - ASGI app `itta_api.domains.assetmgmt.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `asset_id` | `assetId` | Asset Id | yes |
| `tag_value` | `tagValue` | Tag Value | yes |
| `tag_type` | `tagType` | Tag Type | no |
| `printed_on` | `printedOn` | Printed On | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/asset-tags` search
* `GET /api/v1/asset-tags/meta` field catalogue
* `POST /api/v1/asset-tags/reconcile?apply=false` reconcile external records
* `GET /api/v1/asset-tags/export` CSV
