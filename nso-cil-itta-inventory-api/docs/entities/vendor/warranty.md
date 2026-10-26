# Warranty

Domain: **Vendor and Model** (`vendor`) - ASGI app `itta_api.domains.vendor.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `asset_id` | `assetId` | Asset Id | yes |
| `start_date` | `startDate` | Start Date | yes |
| `end_date` | `endDate` | End Date | yes |
| `warranty_type` | `warrantyType` | Warranty Type | no |
| `terms` | `terms` | Terms | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/warranties` search
* `GET /api/v1/warranties/meta` field catalogue
* `POST /api/v1/warranties/reconcile?apply=false` reconcile external records
* `GET /api/v1/warranties/export` CSV
