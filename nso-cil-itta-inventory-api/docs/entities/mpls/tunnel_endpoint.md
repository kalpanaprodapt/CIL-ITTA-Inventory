# Tunnel Endpoint

Domain: **MPLS and VPN** (`mpls`) - ASGI app `itta_api.domains.mpls.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `device_id` | `deviceId` | Device Id | yes |
| `tunnel_name` | `tunnelName` | Tunnel Name | yes |
| `tunnel_type` | `tunnelType` | Tunnel Type | no |
| `source_ip` | `sourceIp` | Source Ip | no |
| `destination_ip` | `destinationIp` | Destination Ip | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/tunnel-endpoints` search
* `GET /api/v1/tunnel-endpoints/meta` field catalogue
* `POST /api/v1/tunnel-endpoints/reconcile?apply=false` reconcile external records
* `GET /api/v1/tunnel-endpoints/export` CSV
