# Lag Group feature

Route prefix `/port/lag-groups` - folder `src/features/port/lag-group`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `deviceId` | Device Id | yes | `u` |
| `lagName` | Lag Name | yes | `s` |
| `memberCount` | Member Count | no | `i` |
| `lacpMode` | Lacp Mode | no | `e` |
| `minLinks` | Min Links | no | `i` |
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
* Device Id
* Lag Name
* Member Count
* Lacp Mode
* Min Links
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `LagGroupListPage` - table, filters, bulk actions, CSV export
* `LagGroupDetailPage` - read only view with status buttons
* `LagGroupCreatePage` / `LagGroupEditPage` - shared `LagGroupForm`
