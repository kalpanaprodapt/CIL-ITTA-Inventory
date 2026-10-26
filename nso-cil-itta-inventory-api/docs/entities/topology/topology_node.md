# Topology Node

Domain: **Topology and Links** (`topology`) - ASGI app `itta_api.domains.topology.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `view_id` | `viewId` | View Id | yes |
| `entity_ref` | `entityRef` | Entity Ref | yes |
| `node_type` | `nodeType` | Node Type | no |
| `x_pos` | `xPos` | X Pos | no |
| `y_pos` | `yPos` | Y Pos | no |
| `layer` | `layer` | Layer | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/topology-nodes` search
* `GET /api/v1/topology-nodes/meta` field catalogue
* `POST /api/v1/topology-nodes/reconcile?apply=false` reconcile external records
* `GET /api/v1/topology-nodes/export` CSV
