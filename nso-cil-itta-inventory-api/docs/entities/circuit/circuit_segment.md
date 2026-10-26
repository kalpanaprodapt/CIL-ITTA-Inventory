# Circuit Segment

Domain: **Circuits** (`circuit`) - ASGI app `itta_api.domains.circuit.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `circuit_id` | `circuitId` | Circuit Id | yes |
| `segment_order` | `segmentOrder` | Segment Order | yes |
| `a_termination_id` | `aTerminationId` | A Termination Id | no |
| `z_termination_id` | `zTerminationId` | Z Termination Id | no |
| `latency_ms` | `latencyMs` | Latency Ms | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/circuit-segments` search
* `GET /api/v1/circuit-segments/meta` field catalogue
* `POST /api/v1/circuit-segments/reconcile?apply=false` reconcile external records
* `GET /api/v1/circuit-segments/export` CSV
