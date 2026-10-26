# Reservation feature

Route prefix `/capacity/reservations` - folder `src/features/capacity/reservation`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `resourceRef` | Resource Ref | yes | `s` |
| `reservedFor` | Reserved For | yes | `s` |
| `quantity` | Quantity | yes | `d` |
| `startDate` | Start Date | yes | `D` |
| `endDate` | End Date | no | `D` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Resource Ref
* Reserved For
* Quantity
* Start Date
* End Date
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `ReservationListPage` - table, filters, bulk actions, CSV export
* `ReservationDetailPage` - read only view with status buttons
* `ReservationCreatePage` / `ReservationEditPage` - shared `ReservationForm`
