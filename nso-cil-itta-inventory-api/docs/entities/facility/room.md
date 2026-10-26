# Room

Domain: **Facility** (`facility`) - ASGI app `itta_api.domains.facility.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `floor_id` | `floorId` | Floor Id | yes |
| `room_type` | `roomType` | Room Type | no |
| `area_sqm` | `areaSqm` | Area Sqm | no |
| `cooling_tons` | `coolingTons` | Cooling Tons | no |
| `fire_zone` | `fireZone` | Fire Zone | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/rooms` search
* `GET /api/v1/rooms/meta` field catalogue
* `POST /api/v1/rooms/reconcile?apply=false` reconcile external records
* `GET /api/v1/rooms/export` CSV
