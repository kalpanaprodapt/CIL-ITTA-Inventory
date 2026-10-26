# Circuit Termination

Domain: **Circuits** (`circuit`) - ASGI app `itta_api.domains.circuit.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `circuit_id` | `circuitId` | Circuit Id | yes |
| `side` | `side` | Side | no |
| `port_id` | `portId` | Port Id | yes |
| `xconnect_id` | `xconnectId` | Xconnect Id | no |
| `patch_info` | `patchInfo` | Patch Info | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/circuit-terminations` search
* `GET /api/v1/circuit-terminations/meta` field catalogue
* `POST /api/v1/circuit-terminations/reconcile?apply=false` reconcile external records
* `GET /api/v1/circuit-terminations/export` CSV
