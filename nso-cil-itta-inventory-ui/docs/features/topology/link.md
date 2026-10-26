# Link feature

Route prefix `/topology/links` - folder `src/features/topology/link`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `aInterfaceId` | A Interface Id | yes | `u` |
| `zInterfaceId` | Z Interface Id | yes | `u` |
| `linkType` | Link Type | no | `e` |
| `speedMbps` | Speed Mbps | no | `l` |
| `discoveredBy` | Discovered By | no | `s` |
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
* A Interface Id
* Z Interface Id
* Link Type
* Speed Mbps
* Discovered By
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `LinkListPage` - table, filters, bulk actions, CSV export
* `LinkDetailPage` - read only view with status buttons
* `LinkCreatePage` / `LinkEditPage` - shared `LinkForm`
