# Cable feature

Route prefix `/cabling/cables` - folder `src/features/cabling/cable`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `cableType` | Cable Type | no | `e` |
| `lengthM` | Length M | no | `d` |
| `aEndPortId` | A End Port Id | no | `u` |
| `zEndPortId` | Z End Port Id | no | `u` |
| `color` | Color | no | `s` |
| `label` | Label | no | `s` |
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
* Cable Type
* Length M
* A End Port Id
* Z End Port Id
* Color
* Label
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `CableListPage` - table, filters, bulk actions, CSV export
* `CableDetailPage` - read only view with status buttons
* `CableCreatePage` / `CableEditPage` - shared `CableForm`
