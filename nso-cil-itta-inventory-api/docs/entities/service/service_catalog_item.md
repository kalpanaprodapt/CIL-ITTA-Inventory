# Service Catalog Item

Domain: **Services** (`service`) - ASGI app `itta_api.domains.service.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `item_name` | `itemName` | Item Name | yes |
| `family` | `family` | Family | no |
| `base_price` | `basePrice` | Base Price | no |
| `billing_unit` | `billingUnit` | Billing Unit | no |
| `orderable` | `orderable` | Orderable | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/service-catalog-items` search
* `GET /api/v1/service-catalog-items/meta` field catalogue
* `POST /api/v1/service-catalog-items/reconcile?apply=false` reconcile external records
* `GET /api/v1/service-catalog-items/export` CSV
