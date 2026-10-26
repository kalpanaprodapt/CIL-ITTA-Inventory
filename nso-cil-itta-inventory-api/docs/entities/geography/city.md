# City

Domain: **Geography** (`geography`) - ASGI app `itta_api.domains.geography.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `region_id` | `regionId` | Region Id | yes |
| `postal_prefix` | `postalPrefix` | Postal Prefix | no |
| `population` | `population` | Population | no |
| `latitude` | `latitude` | Latitude | no |
| `longitude` | `longitude` | Longitude | no |
| `metro_area` | `metroArea` | Metro Area | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/cities` search
* `GET /api/v1/cities/meta` field catalogue
* `POST /api/v1/cities/reconcile?apply=false` reconcile external records
* `GET /api/v1/cities/export` CSV
