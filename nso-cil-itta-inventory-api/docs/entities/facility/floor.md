# Floor

Domain: **Facility** (`facility`) - ASGI app `itta_api.domains.facility.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `building_id` | `buildingId` | Building Id | yes |
| `level` | `level` | Level | no |
| `area_sqm` | `areaSqm` | Area Sqm | no |
| `load_capacity_kg` | `loadCapacityKg` | Load Capacity Kg | no |
| `has_raised_floor` | `hasRaisedFloor` | Has Raised Floor | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/floors` search
* `GET /api/v1/floors/meta` field catalogue
* `POST /api/v1/floors/reconcile?apply=false` reconcile external records
* `GET /api/v1/floors/export` CSV
