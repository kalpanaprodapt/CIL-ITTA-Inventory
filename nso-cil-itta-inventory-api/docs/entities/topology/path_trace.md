# Path Trace

Domain: **Topology and Links** (`topology`) - ASGI app `itta_api.domains.topology.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `source_ref` | `sourceRef` | Source Ref | yes |
| `target_ref` | `targetRef` | Target Ref | yes |
| `hop_count` | `hopCount` | Hop Count | no |
| `total_latency_ms` | `totalLatencyMs` | Total Latency Ms | no |
| `trace_method` | `traceMethod` | Trace Method | no |
| `traced_at` | `tracedAt` | Traced At | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/path-traces` search
* `GET /api/v1/path-traces/meta` field catalogue
* `POST /api/v1/path-traces/reconcile?apply=false` reconcile external records
* `GET /api/v1/path-traces/export` CSV
