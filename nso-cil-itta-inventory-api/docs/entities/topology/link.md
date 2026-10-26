# Link

Domain: **Topology and Links** (`topology`) - ASGI app `itta_api.domains.topology.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `a_interface_id` | `aInterfaceId` | A Interface Id | yes |
| `z_interface_id` | `zInterfaceId` | Z Interface Id | yes |
| `link_type` | `linkType` | Link Type | no |
| `speed_mbps` | `speedMbps` | Speed Mbps | no |
| `discovered_by` | `discoveredBy` | Discovered By | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/links` search
* `GET /api/v1/links/meta` field catalogue
* `POST /api/v1/links/reconcile?apply=false` reconcile external records
* `GET /api/v1/links/export` CSV
