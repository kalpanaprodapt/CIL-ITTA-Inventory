# Vlan feature

Route prefix `/switching/vlans` - folder `src/features/switching/vlan`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vlanId` | Vlan Id | yes | `i` |
| `vlanName` | Vlan Name | yes | `s` |
| `vlanType` | Vlan Type | no | `e` |
| `poolId` | Pool Id | no | `u` |
| `stpMode` | Stp Mode | no | `s` |
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
* Vlan Id
* Vlan Name
* Vlan Type
* Pool Id
* Stp Mode
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `VlanListPage` - table, filters, bulk actions, CSV export
* `VlanDetailPage` - read only view with status buttons
* `VlanCreatePage` / `VlanEditPage` - shared `VlanForm`
