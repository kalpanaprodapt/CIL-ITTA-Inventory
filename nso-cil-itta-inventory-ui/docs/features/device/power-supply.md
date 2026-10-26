# Power Supply feature

Route prefix `/device/power-supplies` - folder `src/features/device/power-supply`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `chassisId` | Chassis Id | yes | `u` |
| `slotNumber` | Slot Number | no | `i` |
| `wattage` | Wattage | no | `i` |
| `feedId` | Feed Id | no | `u` |
| `serialNumber` | Serial Number | no | `s` |
| `efficiency` | Efficiency | no | `d` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Chassis Id
* Slot Number
* Wattage
* Feed Id
* Serial Number
* Efficiency
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `PowerSupplyListPage` - table, filters, bulk actions, CSV export
* `PowerSupplyDetailPage` - read only view with status buttons
* `PowerSupplyCreatePage` / `PowerSupplyEditPage` - shared `PowerSupplyForm`
