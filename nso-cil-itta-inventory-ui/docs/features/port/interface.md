# Interface feature

Route prefix `/port/interfaces` - folder `src/features/port/interface`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `portId` | Port Id | yes | `u` |
| `ifName` | If Name | yes | `s` |
| `ifIndex` | If Index | no | `i` |
| `macAddress` | Mac Address | no | `s` |
| `mtu` | Mtu | no | `i` |
| `operState` | Oper State | no | `e` |
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
* Port Id
* If Name
* If Index
* Mac Address
* Mtu
* Oper State
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `InterfaceListPage` - table, filters, bulk actions, CSV export
* `InterfaceDetailPage` - read only view with status buttons
* `InterfaceCreatePage` / `InterfaceEditPage` - shared `InterfaceForm`
