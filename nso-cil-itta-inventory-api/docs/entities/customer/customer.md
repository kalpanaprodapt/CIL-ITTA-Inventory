# Customer

Domain: **Customers and SLA** (`customer`) - ASGI app `itta_api.domains.customer.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `legal_name` | `legalName` | Legal Name | yes |
| `segment` | `segment` | Segment | no |
| `tax_id` | `taxId` | Tax Id | no |
| `industry` | `industry` | Industry | no |
| `account_manager` | `accountManager` | Account Manager | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/customers` search
* `GET /api/v1/customers/meta` field catalogue
* `POST /api/v1/customers/reconcile?apply=false` reconcile external records
* `GET /api/v1/customers/export` CSV
