# Sub Interface

Domain: **Port and Interface** (`port`) - ASGI app `itta_api.domains.port.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `interface_id` | `interfaceId` | Interface Id | yes |
| `vlan_tag` | `vlanTag` | Vlan Tag | yes |
| `encapsulation` | `encapsulation` | Encapsulation | no |
| `ipv4_address` | `ipv4Address` | Ipv4 Address | no |
| `ipv6_address` | `ipv6Address` | Ipv6 Address | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/sub-interfaces` search
* `GET /api/v1/sub-interfaces/meta` field catalogue
* `POST /api/v1/sub-interfaces/reconcile?apply=false` reconcile external records
* `GET /api/v1/sub-interfaces/export` CSV
