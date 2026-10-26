# Port feature

Route prefix `/port/ports` - folder `src/features/port/port`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `deviceId` | Device Id | yes | `u` |
| `cardId` | Card Id | no | `u` |
| `portNumber` | Port Number | yes | `s` |
| `speedMbps` | Speed Mbps | no | `l` |
| `mediaType` | Media Type | no | `e` |
| `adminState` | Admin State | no | `e` |
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
* Device Id
* Card Id
* Port Number
* Speed Mbps
* Media Type
* Admin State
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `PortListPage` - table, filters, bulk actions, CSV export
* `PortDetailPage` - read only view with status buttons
* `PortCreatePage` / `PortEditPage` - shared `PortForm`
