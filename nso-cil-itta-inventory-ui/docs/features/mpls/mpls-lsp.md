# Mpls Lsp feature

Route prefix `/mpls/mpls-lsps` - folder `src/features/mpls/mpls-lsp`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `lspName` | Lsp Name | yes | `s` |
| `headEndId` | Head End Id | yes | `u` |
| `tailEndId` | Tail End Id | yes | `u` |
| `bandwidthMbps` | Bandwidth Mbps | no | `l` |
| `setupPriority` | Setup Priority | no | `i` |
| `pathType` | Path Type | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Lsp Name
* Head End Id
* Tail End Id
* Bandwidth Mbps
* Setup Priority
* Path Type
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `MplsLspListPage` - table, filters, bulk actions, CSV export
* `MplsLspDetailPage` - read only view with status buttons
* `MplsLspCreatePage` / `MplsLspEditPage` - shared `MplsLspForm`
