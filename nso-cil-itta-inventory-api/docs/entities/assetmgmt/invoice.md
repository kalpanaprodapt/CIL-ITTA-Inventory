# Invoice

Domain: **Asset and Finance** (`assetmgmt`) - ASGI app `itta_api.domains.assetmgmt.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `invoice_number` | `invoiceNumber` | Invoice Number | yes |
| `po_id` | `poId` | Po Id | no |
| `amount` | `amount` | Amount | yes |
| `due_date` | `dueDate` | Due Date | no |
| `paid` | `paid` | Paid | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/invoices` search
* `GET /api/v1/invoices/meta` field catalogue
* `POST /api/v1/invoices/reconcile?apply=false` reconcile external records
* `GET /api/v1/invoices/export` CSV
