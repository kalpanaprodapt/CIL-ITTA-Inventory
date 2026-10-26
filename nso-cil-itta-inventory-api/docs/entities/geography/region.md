# Region

Domain: **Geography** (`geography`) - ASGI app `itta_api.domains.geography.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `country_id` | `countryId` | Country Id | yes |
| `region_type` | `regionType` | Region Type | no |
| `population` | `population` | Population | no |
| `latitude` | `latitude` | Latitude | no |
| `longitude` | `longitude` | Longitude | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/regions` search
* `GET /api/v1/regions/meta` field catalogue
* `POST /api/v1/regions/reconcile?apply=false` reconcile external records
* `GET /api/v1/regions/export` CSV
