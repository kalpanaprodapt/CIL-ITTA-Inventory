# Service Order feature

Route prefix `/service/service-orders` - folder `src/features/service/service-order`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `customerId` | Customer Id | yes | `u` |
| `orderType` | Order Type | no | `e` |
| `requestedOn` | Requested On | yes | `D` |
| `targetDate` | Target Date | no | `D` |
| `priority` | Priority | no | `i` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Customer Id
* Order Type
* Requested On
* Target Date
* Priority
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `ServiceOrderListPage` - table, filters, bulk actions, CSV export
* `ServiceOrderDetailPage` - read only view with status buttons
* `ServiceOrderCreatePage` / `ServiceOrderEditPage` - shared `ServiceOrderForm`
