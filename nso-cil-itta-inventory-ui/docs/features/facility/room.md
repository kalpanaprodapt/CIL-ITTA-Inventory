# Room feature

Route prefix `/facility/rooms` - folder `src/features/facility/room`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `floorId` | Floor Id | yes | `u` |
| `roomType` | Room Type | no | `e` |
| `areaSqm` | Area Sqm | no | `d` |
| `coolingTons` | Cooling Tons | no | `d` |
| `fireZone` | Fire Zone | no | `s` |
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
* Floor Id
* Room Type
* Area Sqm
* Cooling Tons
* Fire Zone
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `RoomListPage` - table, filters, bulk actions, CSV export
* `RoomDetailPage` - read only view with status buttons
* `RoomCreatePage` / `RoomEditPage` - shared `RoomForm`
