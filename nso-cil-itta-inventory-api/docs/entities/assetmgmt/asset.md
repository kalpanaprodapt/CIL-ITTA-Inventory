# Asset

Domain: **Asset and Finance** (`assetmgmt`) - ASGI app `itta_api.domains.assetmgmt.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `asset_tag` | `assetTag` | Asset Tag | yes |
| `asset_class` | `assetClass` | Asset Class | yes |
| `acquired_on` | `acquiredOn` | Acquired On | no |
| `purchase_price` | `purchasePrice` | Purchase Price | no |
| `current_value` | `currentValue` | Current Value | no |
| `custodian` | `custodian` | Custodian | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/assets` search
* `GET /api/v1/assets/meta` field catalogue
* `POST /api/v1/assets/reconcile?apply=false` reconcile external records
* `GET /api/v1/assets/export` CSV
