# Pseudo Wire feature

Route prefix `/mpls/pseudo-wires` - folder `src/features/mpls/pseudo-wire`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vcId` | Vc Id | yes | `l` |
| `aEndId` | A End Id | yes | `u` |
| `zEndId` | Z End Id | yes | `u` |
| `encapsulation` | Encapsulation | no | `s` |
| `controlWord` | Control Word | no | `b` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Vc Id
* A End Id
* Z End Id
* Encapsulation
* Control Word
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `PseudoWireListPage` - table, filters, bulk actions, CSV export
* `PseudoWireDetailPage` - read only view with status buttons
* `PseudoWireCreatePage` / `PseudoWireEditPage` - shared `PseudoWireForm`
