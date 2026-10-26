# Rack feature

Route prefix `/facility/racks` - folder `src/features/facility/rack`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `roomId` | Room Id | yes | `u` |
| `rackUnits` | Rack Units | yes | `i` |
| `widthMm` | Width Mm | no | `i` |
| `depthMm` | Depth Mm | no | `i` |
| `maxWeightKg` | Max Weight Kg | no | `d` |
| `rowLabel` | Row Label | no | `s` |
| `position` | Position | no | `s` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Room Id
* Rack Units
* Width Mm
* Depth Mm
* Max Weight Kg
* Row Label
* Position
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `RackListPage` - table, filters, bulk actions, CSV export
* `RackDetailPage` - read only view with status buttons
* `RackCreatePage` / `RackEditPage` - shared `RackForm`
