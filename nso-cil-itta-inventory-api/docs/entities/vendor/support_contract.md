# Support Contract

Domain: **Vendor and Model** (`vendor`) - ASGI app `itta_api.domains.vendor.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vendor_id` | `vendorId` | Vendor Id | yes |
| `contract_number` | `contractNumber` | Contract Number | yes |
| `start_date` | `startDate` | Start Date | yes |
| `end_date` | `endDate` | End Date | yes |
| `coverage_level` | `coverageLevel` | Coverage Level | no |
| `annual_cost` | `annualCost` | Annual Cost | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/support-contracts` search
* `GET /api/v1/support-contracts/meta` field catalogue
* `POST /api/v1/support-contracts/reconcile?apply=false` reconcile external records
* `GET /api/v1/support-contracts/export` CSV
