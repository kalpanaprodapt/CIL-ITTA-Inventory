# Wdm System feature

Route prefix `/optical/wdm-systems` - folder `src/features/optical/wdm-system`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `systemName` | System Name | yes | `s` |
| `channelCapacity` | Channel Capacity | yes | `i` |
| `band` | Band | no | `e` |
| `spanCount` | Span Count | no | `i` |
| `vendorId` | Vendor Id | no | `u` |
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
* System Name
* Channel Capacity
* Band
* Span Count
* Vendor Id
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `WdmSystemListPage` - table, filters, bulk actions, CSV export
* `WdmSystemDetailPage` - read only view with status buttons
* `WdmSystemCreatePage` / `WdmSystemEditPage` - shared `WdmSystemForm`
