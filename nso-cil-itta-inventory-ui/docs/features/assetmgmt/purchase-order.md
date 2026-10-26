# Purchase Order feature

Route prefix `/assetmgmt/purchase-orders` - folder `src/features/assetmgmt/purchase-order`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `poNumber` | Po Number | yes | `s` |
| `vendorId` | Vendor Id | yes | `u` |
| `orderedOn` | Ordered On | yes | `D` |
| `totalAmount` | Total Amount | yes | `d` |
| `currency` | Currency | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Po Number
* Vendor Id
* Ordered On
* Total Amount
* Currency
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `PurchaseOrderListPage` - table, filters, bulk actions, CSV export
* `PurchaseOrderDetailPage` - read only view with status buttons
* `PurchaseOrderCreatePage` / `PurchaseOrderEditPage` - shared `PurchaseOrderForm`
