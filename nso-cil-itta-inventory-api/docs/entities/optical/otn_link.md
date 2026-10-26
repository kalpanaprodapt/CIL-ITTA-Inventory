# Otn Link

Domain: **Optical** (`optical`) - ASGI app `itta_api.domains.optical.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `a_node_id` | `aNodeId` | A Node Id | yes |
| `z_node_id` | `zNodeId` | Z Node Id | yes |
| `odu` | `odu` | Odu | no |
| `latency_us` | `latencyUs` | Latency Us | no |
| `protection` | `protection` | Protection | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/otn-links` search
* `GET /api/v1/otn-links/meta` field catalogue
* `POST /api/v1/otn-links/reconcile?apply=false` reconcile external records
* `GET /api/v1/otn-links/export` CSV
