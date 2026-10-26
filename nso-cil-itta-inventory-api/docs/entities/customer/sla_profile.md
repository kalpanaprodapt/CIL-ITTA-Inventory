# Sla Profile

Domain: **Customers and SLA** (`customer`) - ASGI app `itta_api.domains.customer.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `profile_name` | `profileName` | Profile Name | yes |
| `availability_pct` | `availabilityPct` | Availability Pct | yes |
| `mttr_minutes` | `mttrMinutes` | Mttr Minutes | no |
| `latency_ms` | `latencyMs` | Latency Ms | no |
| `jitter_ms` | `jitterMs` | Jitter Ms | no |
| `credit_policy` | `creditPolicy` | Credit Policy | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/sla-profiles` search
* `GET /api/v1/sla-profiles/meta` field catalogue
* `POST /api/v1/sla-profiles/reconcile?apply=false` reconcile external records
* `GET /api/v1/sla-profiles/export` CSV
