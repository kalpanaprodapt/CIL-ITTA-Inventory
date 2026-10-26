# Purchase Order

Domain: **Asset and Finance** (`assetmgmt`) - ASGI app `itta_api.domains.assetmgmt.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `po_number` | `poNumber` | Po Number | yes |
| `vendor_id` | `vendorId` | Vendor Id | yes |
| `ordered_on` | `orderedOn` | Ordered On | yes |
| `total_amount` | `totalAmount` | Total Amount | yes |
| `currency` | `currency` | Currency | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/purchase-orders` search
* `GET /api/v1/purchase-orders/meta` field catalogue
* `POST /api/v1/purchase-orders/reconcile?apply=false` reconcile external records
* `GET /api/v1/purchase-orders/export` CSV
