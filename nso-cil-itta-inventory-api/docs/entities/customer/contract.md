# Contract

Domain: **Customers and SLA** (`customer`) - ASGI app `itta_api.domains.customer.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `customer_id` | `customerId` | Customer Id | yes |
| `contract_number` | `contractNumber` | Contract Number | yes |
| `start_date` | `startDate` | Start Date | yes |
| `end_date` | `endDate` | End Date | no |
| `term_months` | `termMonths` | Term Months | no |
| `auto_renew` | `autoRenew` | Auto Renew | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/contracts` search
* `GET /api/v1/contracts/meta` field catalogue
* `POST /api/v1/contracts/reconcile?apply=false` reconcile external records
* `GET /api/v1/contracts/export` CSV
