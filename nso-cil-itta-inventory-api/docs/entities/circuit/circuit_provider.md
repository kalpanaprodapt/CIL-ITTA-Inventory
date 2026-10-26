# Circuit Provider

Domain: **Circuits** (`circuit`) - ASGI app `itta_api.domains.circuit.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `provider_name` | `providerName` | Provider Name | yes |
| `account_number` | `accountNumber` | Account Number | no |
| `noc` | `noc` | Noc | no |
| `escalation_email` | `escalationEmail` | Escalation Email | no |
| `provider_type` | `providerType` | Provider Type | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/circuit-providers` search
* `GET /api/v1/circuit-providers/meta` field catalogue
* `POST /api/v1/circuit-providers/reconcile?apply=false` reconcile external records
* `GET /api/v1/circuit-providers/export` CSV
