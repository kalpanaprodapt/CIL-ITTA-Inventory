# Asset feature

Route prefix `/assetmgmt/assets` - folder `src/features/assetmgmt/asset`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `assetTag` | Asset Tag | yes | `s` |
| `assetClass` | Asset Class | yes | `s` |
| `acquiredOn` | Acquired On | no | `D` |
| `purchasePrice` | Purchase Price | no | `d` |
| `currentValue` | Current Value | no | `d` |
| `custodian` | Custodian | no | `s` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Asset Tag
* Asset Class
* Acquired On
* Purchase Price
* Current Value
* Custodian
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `AssetListPage` - table, filters, bulk actions, CSV export
* `AssetDetailPage` - read only view with status buttons
* `AssetCreatePage` / `AssetEditPage` - shared `AssetForm`
