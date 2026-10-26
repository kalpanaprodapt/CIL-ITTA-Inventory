# Hardware Model

Domain: **Vendor and Model** (`vendor`) - ASGI app `itta_api.domains.vendor.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vendor_id` | `vendorId` | Vendor Id | yes |
| `model_number` | `modelNumber` | Model Number | yes |
| `form_factor` | `formFactor` | Form Factor | no |
| `power_watts` | `powerWatts` | Power Watts | no |
| `eol_date` | `eolDate` | Eol Date | no |
| `eos_date` | `eosDate` | Eos Date | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/hardware-models` search
* `GET /api/v1/hardware-models/meta` field catalogue
* `POST /api/v1/hardware-models/reconcile?apply=false` reconcile external records
* `GET /api/v1/hardware-models/export` CSV
