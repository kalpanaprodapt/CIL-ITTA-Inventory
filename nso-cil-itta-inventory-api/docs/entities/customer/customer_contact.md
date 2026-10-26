# Customer Contact

Domain: **Customers and SLA** (`customer`) - ASGI app `itta_api.domains.customer.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `customer_id` | `customerId` | Customer Id | yes |
| `full_name` | `fullName` | Full Name | yes |
| `email` | `email` | Email | no |
| `phone` | `phone` | Phone | no |
| `contact_role` | `contactRole` | Contact Role | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/customer-contacts` search
* `GET /api/v1/customer-contacts/meta` field catalogue
* `POST /api/v1/customer-contacts/reconcile?apply=false` reconcile external records
* `GET /api/v1/customer-contacts/export` CSV
