# Asset Tag feature

Route prefix `/assetmgmt/asset-tags` - folder `src/features/assetmgmt/asset-tag`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `assetId` | Asset Id | yes | `u` |
| `tagValue` | Tag Value | yes | `s` |
| `tagType` | Tag Type | no | `e` |
| `printedOn` | Printed On | no | `D` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Asset Id
* Tag Value
* Tag Type
* Printed On
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `AssetTagListPage` - table, filters, bulk actions, CSV export
* `AssetTagDetailPage` - read only view with status buttons
* `AssetTagCreatePage` / `AssetTagEditPage` - shared `AssetTagForm`
