# Patch Port feature

Route prefix `/cabling/patch-ports` - folder `src/features/cabling/patch-port`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `panelId` | Panel Id | yes | `u` |
| `portNumber` | Port Number | yes | `i` |
| `connectedCableId` | Connected Cable Id | no | `u` |
| `polarity` | Polarity | no | `s` |
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
* Panel Id
* Port Number
* Connected Cable Id
* Polarity
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `PatchPortListPage` - table, filters, bulk actions, CSV export
* `PatchPortDetailPage` - read only view with status buttons
* `PatchPortCreatePage` / `PatchPortEditPage` - shared `PatchPortForm`
