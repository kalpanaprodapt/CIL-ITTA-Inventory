# Work Order feature

Route prefix `/change/work-orders` - folder `src/features/change/work-order`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `workOrderNumber` | Work Order Number | yes | `s` |
| `workType` | Work Type | yes | `s` |
| `assignedTechnicianId` | Assigned Technician Id | no | `u` |
| `dueDate` | Due Date | no | `D` |
| `workState` | Work State | no | `e` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Work Order Number
* Work Type
* Assigned Technician Id
* Due Date
* Work State
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `WorkOrderListPage` - table, filters, bulk actions, CSV export
* `WorkOrderDetailPage` - read only view with status buttons
* `WorkOrderCreatePage` / `WorkOrderEditPage` - shared `WorkOrderForm`
