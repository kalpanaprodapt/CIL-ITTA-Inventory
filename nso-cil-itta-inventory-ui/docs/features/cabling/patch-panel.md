# Patch Panel feature

Route prefix `/cabling/patch-panels` - folder `src/features/cabling/patch-panel`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `roomId` | Room Id | yes | `u` |
| `rackId` | Rack Id | no | `u` |
| `panelType` | Panel Type | no | `e` |
| `portCount` | Port Count | yes | `i` |
| `label` | Label | no | `s` |
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
* Room Id
* Rack Id
* Panel Type
* Port Count
* Label
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `PatchPanelListPage` - table, filters, bulk actions, CSV export
* `PatchPanelDetailPage` - read only view with status buttons
* `PatchPanelCreatePage` / `PatchPanelEditPage` - shared `PatchPanelForm`
