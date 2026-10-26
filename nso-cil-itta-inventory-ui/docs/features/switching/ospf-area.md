# Ospf Area feature

Route prefix `/switching/ospf-areas` - folder `src/features/switching/ospf-area`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `areaId` | Area Id | yes | `s` |
| `areaType` | Area Type | no | `e` |
| `deviceCount` | Device Count | no | `i` |
| `authType` | Auth Type | no | `s` |
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
* Area Id
* Area Type
* Device Count
* Auth Type
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `OspfAreaListPage` - table, filters, bulk actions, CSV export
* `OspfAreaDetailPage` - read only view with status buttons
* `OspfAreaCreatePage` / `OspfAreaEditPage` - shared `OspfAreaForm`
