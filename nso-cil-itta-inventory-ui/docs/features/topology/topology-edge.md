# Topology Edge feature

Route prefix `/topology/topology-edges` - folder `src/features/topology/topology-edge`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `viewId` | View Id | yes | `u` |
| `sourceNodeId` | Source Node Id | yes | `u` |
| `targetNodeId` | Target Node Id | yes | `u` |
| `weight` | Weight | no | `d` |
| `directed` | Directed | no | `b` |
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
* View Id
* Source Node Id
* Target Node Id
* Weight
* Directed
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `TopologyEdgeListPage` - table, filters, bulk actions, CSV export
* `TopologyEdgeDetailPage` - read only view with status buttons
* `TopologyEdgeCreatePage` / `TopologyEdgeEditPage` - shared `TopologyEdgeForm`
