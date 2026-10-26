# Roadm feature

Route prefix `/optical/roadms` - folder `src/features/optical/roadm`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `systemId` | System Id | yes | `u` |
| `degrees` | Degrees | yes | `i` |
| `colorless` | Colorless | no | `b` |
| `directionless` | Directionless | no | `b` |
| `contentionless` | Contentionless | no | `b` |
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
* System Id
* Degrees
* Colorless
* Directionless
* Contentionless
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `RoadmListPage` - table, filters, bulk actions, CSV export
* `RoadmDetailPage` - read only view with status buttons
* `RoadmCreatePage` / `RoadmEditPage` - shared `RoadmForm`
