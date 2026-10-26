# Mpls Lsp

Domain: **MPLS and VPN** (`mpls`) - ASGI app `itta_api.domains.mpls.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `lsp_name` | `lspName` | Lsp Name | yes |
| `head_end_id` | `headEndId` | Head End Id | yes |
| `tail_end_id` | `tailEndId` | Tail End Id | yes |
| `bandwidth_mbps` | `bandwidthMbps` | Bandwidth Mbps | no |
| `setup_priority` | `setupPriority` | Setup Priority | no |
| `path_type` | `pathType` | Path Type | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/mpls-lsps` search
* `GET /api/v1/mpls-lsps/meta` field catalogue
* `POST /api/v1/mpls-lsps/reconcile?apply=false` reconcile external records
* `GET /api/v1/mpls-lsps/export` CSV
