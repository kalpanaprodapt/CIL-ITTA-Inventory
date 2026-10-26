# Service Catalog Item feature

Route prefix `/service/service-catalog-items` - folder `src/features/service/service-catalog-item`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `itemName` | Item Name | yes | `s` |
| `family` | Family | no | `e` |
| `basePrice` | Base Price | no | `d` |
| `billingUnit` | Billing Unit | no | `s` |
| `orderable` | Orderable | no | `b` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Item Name
* Family
* Base Price
* Billing Unit
* Orderable
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `ServiceCatalogItemListPage` - table, filters, bulk actions, CSV export
* `ServiceCatalogItemDetailPage` - read only view with status buttons
* `ServiceCatalogItemCreatePage` / `ServiceCatalogItemEditPage` - shared `ServiceCatalogItemForm`
