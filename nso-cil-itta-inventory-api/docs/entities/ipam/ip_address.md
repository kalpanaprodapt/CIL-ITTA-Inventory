# Ip Address

Domain: **IP Management** (`ipam`) - ASGI app `itta_api.domains.ipam.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `subnet_id` | `subnetId` | Subnet Id | yes |
| `address` | `address` | Address | yes |
| `assigned_to_id` | `assignedToId` | Assigned To Id | no |
| `assignment_type` | `assignmentType` | Assignment Type | no |
| `dns_name` | `dnsName` | Dns Name | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/ip-addresses` search
* `GET /api/v1/ip-addresses/meta` field catalogue
* `POST /api/v1/ip-addresses/reconcile?apply=false` reconcile external records
* `GET /api/v1/ip-addresses/export` CSV
