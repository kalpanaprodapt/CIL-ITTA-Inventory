# Topology Edge

Domain: **Topology and Links** (`topology`) - ASGI app `itta_api.domains.topology.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `view_id` | `viewId` | View Id | yes |
| `source_node_id` | `sourceNodeId` | Source Node Id | yes |
| `target_node_id` | `targetNodeId` | Target Node Id | yes |
| `weight` | `weight` | Weight | no |
| `directed` | `directed` | Directed | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/topology-edges` search
* `GET /api/v1/topology-edges/meta` field catalogue
* `POST /api/v1/topology-edges/reconcile?apply=false` reconcile external records
* `GET /api/v1/topology-edges/export` CSV
