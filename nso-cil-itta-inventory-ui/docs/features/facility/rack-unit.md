# Rack Unit feature

Route prefix `/facility/rack-units` - folder `src/features/facility/rack-unit`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `rackId` | Rack Id | yes | `u` |
| `unitNumber` | Unit Number | yes | `i` |
| `occupied` | Occupied | no | `b` |
| `mountedDeviceId` | Mounted Device Id | no | `u` |
| `face` | Face | no | `e` |
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
* Rack Id
* Unit Number
* Occupied
* Mounted Device Id
* Face
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `RackUnitListPage` - table, filters, bulk actions, CSV export
* `RackUnitDetailPage` - read only view with status buttons
* `RackUnitCreatePage` / `RackUnitEditPage` - shared `RackUnitForm`
