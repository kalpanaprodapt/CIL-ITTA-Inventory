# Invoice feature

Route prefix `/assetmgmt/invoices` - folder `src/features/assetmgmt/invoice`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `invoiceNumber` | Invoice Number | yes | `s` |
| `poId` | Po Id | no | `u` |
| `amount` | Amount | yes | `d` |
| `dueDate` | Due Date | no | `D` |
| `paid` | Paid | no | `b` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Invoice Number
* Po Id
* Amount
* Due Date
* Paid
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `InvoiceListPage` - table, filters, bulk actions, CSV export
* `InvoiceDetailPage` - read only view with status buttons
* `InvoiceCreatePage` / `InvoiceEditPage` - shared `InvoiceForm`
