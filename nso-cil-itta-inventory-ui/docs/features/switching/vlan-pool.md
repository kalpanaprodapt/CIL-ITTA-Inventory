# Vlan Pool feature

Route prefix `/switching/vlan-pools` - folder `src/features/switching/vlan-pool`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `poolName` | Pool Name | yes | `s` |
| `rangeStart` | Range Start | yes | `i` |
| `rangeEnd` | Range End | yes | `i` |
| `allocated` | Allocated | no | `i` |
| `scope` | Scope | no | `s` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Pool Name
* Range Start
* Range End
* Allocated
* Scope
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `VlanPoolListPage` - table, filters, bulk actions, CSV export
* `VlanPoolDetailPage` - read only view with status buttons
* `VlanPoolCreatePage` / `VlanPoolEditPage` - shared `VlanPoolForm`
