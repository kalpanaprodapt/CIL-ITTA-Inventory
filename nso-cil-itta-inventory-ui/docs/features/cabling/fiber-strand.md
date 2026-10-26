# Fiber Strand feature

Route prefix `/cabling/fiber-strands` - folder `src/features/cabling/fiber-strand`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `cableId` | Cable Id | yes | `u` |
| `strandNumber` | Strand Number | yes | `i` |
| `bufferTube` | Buffer Tube | no | `s` |
| `tubeColor` | Tube Color | no | `s` |
| `attenuationDb` | Attenuation Db | no | `d` |
| `inUse` | In Use | no | `b` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Cable Id
* Strand Number
* Buffer Tube
* Tube Color
* Attenuation Db
* In Use
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `FiberStrandListPage` - table, filters, bulk actions, CSV export
* `FiberStrandDetailPage` - read only view with status buttons
* `FiberStrandCreatePage` / `FiberStrandEditPage` - shared `FiberStrandForm`
