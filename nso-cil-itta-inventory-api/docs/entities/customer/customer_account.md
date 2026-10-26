# Customer Account

Domain: **Customers and SLA** (`customer`) - ASGI app `itta_api.domains.customer.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `customer_id` | `customerId` | Customer Id | yes |
| `account_number` | `accountNumber` | Account Number | yes |
| `billing_cycle` | `billingCycle` | Billing Cycle | no |
| `currency` | `currency` | Currency | no |
| `credit_limit` | `creditLimit` | Credit Limit | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/customer-accounts` search
* `GET /api/v1/customer-accounts/meta` field catalogue
* `POST /api/v1/customer-accounts/reconcile?apply=false` reconcile external records
* `GET /api/v1/customer-accounts/export` CSV
