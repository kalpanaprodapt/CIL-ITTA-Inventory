# Depreciation Schedule

Domain: **Asset and Finance** (`assetmgmt`) - ASGI app `itta_api.domains.assetmgmt.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `asset_id` | `assetId` | Asset Id | yes |
| `method` | `method` | Method | no |
| `useful_life_months` | `usefulLifeMonths` | Useful Life Months | yes |
| `salvage_value` | `salvageValue` | Salvage Value | no |
| `start_date` | `startDate` | Start Date | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/depreciation-schedules` search
* `GET /api/v1/depreciation-schedules/meta` field catalogue
* `POST /api/v1/depreciation-schedules/reconcile?apply=false` reconcile external records
* `GET /api/v1/depreciation-schedules/export` CSV
