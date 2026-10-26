# Backhaul Link

Domain: **Radio and Mobile** (`radio`) - ASGI app `itta_api.domains.radio.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `base_station_id` | `baseStationId` | Base Station Id | yes |
| `link_medium` | `linkMedium` | Link Medium | no |
| `capacity_mbps` | `capacityMbps` | Capacity Mbps | yes |
| `latency_ms` | `latencyMs` | Latency Ms | no |
| `circuit_ref` | `circuitRef` | Circuit Ref | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/backhaul-links` search
* `GET /api/v1/backhaul-links/meta` field catalogue
* `POST /api/v1/backhaul-links/reconcile?apply=false` reconcile external records
* `GET /api/v1/backhaul-links/export` CSV
