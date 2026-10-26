# Sku feature

Route prefix `/vendor/skus` - folder `src/features/vendor/sku`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `modelId` | Model Id | yes | `u` |
| `skuCode` | Sku Code | yes | `s` |
| `listPrice` | List Price | no | `d` |
| `currency` | Currency | no | `s` |
| `leadTimeDays` | Lead Time Days | no | `i` |
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
* Model Id
* Sku Code
* List Price
* Currency
* Lead Time Days
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `SkuListPage` - table, filters, bulk actions, CSV export
* `SkuDetailPage` - read only view with status buttons
* `SkuCreatePage` / `SkuEditPage` - shared `SkuForm`
