# Site

Domain: **Geography** (`geography`) - ASGI app `itta_api.domains.geography.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `city_id` | `cityId` | City Id | yes |
| `site_type` | `siteType` | Site Type | no |
| `address` | `address` | Address | no |
| `latitude` | `latitude` | Latitude | no |
| `longitude` | `longitude` | Longitude | no |
| `power_kw` | `powerKw` | Power Kw | no |
| `access_hours` | `accessHours` | Access Hours | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/sites` search
* `GET /api/v1/sites/meta` field catalogue
* `POST /api/v1/sites/reconcile?apply=false` reconcile external records
* `GET /api/v1/sites/export` CSV
