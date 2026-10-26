# Pseudo Wire

Domain: **MPLS and VPN** (`mpls`) - ASGI app `itta_api.domains.mpls.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vc_id` | `vcId` | Vc Id | yes |
| `a_end_id` | `aEndId` | A End Id | yes |
| `z_end_id` | `zEndId` | Z End Id | yes |
| `encapsulation` | `encapsulation` | Encapsulation | no |
| `control_word` | `controlWord` | Control Word | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/pseudo-wires` search
* `GET /api/v1/pseudo-wires/meta` field catalogue
* `POST /api/v1/pseudo-wires/reconcile?apply=false` reconcile external records
* `GET /api/v1/pseudo-wires/export` CSV
