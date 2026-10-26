# Circuit

Domain: **Circuits** (`circuit`) - ASGI app `itta_api.domains.circuit.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `circuit_id` | `circuitId` | Circuit Id | yes |
| `circuit_type` | `circuitType` | Circuit Type | no |
| `bandwidth_mbps` | `bandwidthMbps` | Bandwidth Mbps | yes |
| `provider_id` | `providerId` | Provider Id | no |
| `a_site_id` | `aSiteId` | A Site Id | no |
| `z_site_id` | `zSiteId` | Z Site Id | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/circuits` search
* `GET /api/v1/circuits/meta` field catalogue
* `POST /api/v1/circuits/reconcile?apply=false` reconcile external records
* `GET /api/v1/circuits/export` CSV
