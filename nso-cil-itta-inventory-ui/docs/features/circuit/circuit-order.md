# Circuit Order feature

Route prefix `/circuit/circuit-orders` - folder `src/features/circuit/circuit-order`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `circuitId` | Circuit Id | no | `u` |
| `orderNumber` | Order Number | yes | `s` |
| `orderedOn` | Ordered On | yes | `D` |
| `requestedDate` | Requested Date | no | `D` |
| `committedDate` | Committed Date | no | `D` |
| `orderStatus` | Order Status | no | `e` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Circuit Id
* Order Number
* Ordered On
* Requested Date
* Committed Date
* Order Status
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `CircuitOrderListPage` - table, filters, bulk actions, CSV export
* `CircuitOrderDetailPage` - read only view with status buttons
* `CircuitOrderCreatePage` / `CircuitOrderEditPage` - shared `CircuitOrderForm`
