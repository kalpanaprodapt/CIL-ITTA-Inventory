# Cloud Account

Domain: **Virtual and Cloud** (`virtual`) - ASGI app `itta_api.domains.virtual.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `provider` | `provider` | Provider | no |
| `account_ref` | `accountRef` | Account Ref | yes |
| `subscription_name` | `subscriptionName` | Subscription Name | no |
| `monthly_budget` | `monthlyBudget` | Monthly Budget | no |
| `environment` | `environment` | Environment | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/cloud-accounts` search
* `GET /api/v1/cloud-accounts/meta` field catalogue
* `POST /api/v1/cloud-accounts/reconcile?apply=false` reconcile external records
* `GET /api/v1/cloud-accounts/export` CSV
