# Sku

Domain: **Vendor and Model** (`vendor`) - ASGI app `itta_api.domains.vendor.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `model_id` | `modelId` | Model Id | yes |
| `sku_code` | `skuCode` | Sku Code | yes |
| `list_price` | `listPrice` | List Price | no |
| `currency` | `currency` | Currency | no |
| `lead_time_days` | `leadTimeDays` | Lead Time Days | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/skus` search
* `GET /api/v1/skus/meta` field catalogue
* `POST /api/v1/skus/reconcile?apply=false` reconcile external records
* `GET /api/v1/skus/export` CSV
