# Ip Subnet

Domain: **IP Management** (`ipam`) - ASGI app `itta_api.domains.ipam.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `cidr` | `cidr` | Cidr | yes |
| `ip_version` | `ipVersion` | Ip Version | no |
| `vrf_id` | `vrfId` | Vrf Id | no |
| `gateway` | `gateway` | Gateway | no |
| `vlan_id` | `vlanId` | Vlan Id | no |
| `utilization_pct` | `utilizationPct` | Utilization Pct | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/ip-subnets` search
* `GET /api/v1/ip-subnets/meta` field catalogue
* `POST /api/v1/ip-subnets/reconcile?apply=false` reconcile external records
* `GET /api/v1/ip-subnets/export` CSV
