# Topology Node feature

Route prefix `/topology/topology-nodes` - folder `src/features/topology/topology-node`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `viewId` | View Id | yes | `u` |
| `entityRef` | Entity Ref | yes | `s` |
| `nodeType` | Node Type | no | `s` |
| `xPos` | X Pos | no | `d` |
| `yPos` | Y Pos | no | `d` |
| `layer` | Layer | no | `i` |
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
* View Id
* Entity Ref
* Node Type
* X Pos
* Y Pos
* Layer
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `TopologyNodeListPage` - table, filters, bulk actions, CSV export
* `TopologyNodeDetailPage` - read only view with status buttons
* `TopologyNodeCreatePage` / `TopologyNodeEditPage` - shared `TopologyNodeForm`
