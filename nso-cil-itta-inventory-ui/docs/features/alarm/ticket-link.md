# Ticket Link feature

Route prefix `/alarm/ticket-links` - folder `src/features/alarm/ticket-link`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `alarmId` | Alarm Id | yes | `u` |
| `ticketSystem` | Ticket System | no | `e` |
| `ticketNumber` | Ticket Number | yes | `s` |
| `linkedAt` | Linked At | no | `T` |
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
* Alarm Id
* Ticket System
* Ticket Number
* Linked At
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `TicketLinkListPage` - table, filters, bulk actions, CSV export
* `TicketLinkDetailPage` - read only view with status buttons
* `TicketLinkCreatePage` / `TicketLinkEditPage` - shared `TicketLinkForm`
