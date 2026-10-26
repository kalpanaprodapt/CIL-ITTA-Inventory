# Cable

Domain: **Cabling** (`cabling`) - ASGI app `itta_api.domains.cabling.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `cable_type` | `cableType` | Cable Type | no |
| `length_m` | `lengthM` | Length M | no |
| `a_end_port_id` | `aEndPortId` | A End Port Id | no |
| `z_end_port_id` | `zEndPortId` | Z End Port Id | no |
| `color` | `color` | Color | no |
| `label` | `label` | Label | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/cables` search
* `GET /api/v1/cables/meta` field catalogue
* `POST /api/v1/cables/reconcile?apply=false` reconcile external records
* `GET /api/v1/cables/export` CSV
