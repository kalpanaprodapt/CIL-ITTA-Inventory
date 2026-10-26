# Vpn Service

Domain: **MPLS and VPN** (`mpls`) - ASGI app `itta_api.domains.mpls.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vpn_name` | `vpnName` | Vpn Name | yes |
| `vpn_type` | `vpnType` | Vpn Type | no |
| `vrf_id` | `vrfId` | Vrf Id | no |
| `customer_id` | `customerId` | Customer Id | no |
| `topology` | `topology` | Topology | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/vpn-services` search
* `GET /api/v1/vpn-services/meta` field catalogue
* `POST /api/v1/vpn-services/reconcile?apply=false` reconcile external records
* `GET /api/v1/vpn-services/export` CSV
