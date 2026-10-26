# Power Feed

Domain: **Facility** (`facility`) - ASGI app `itta_api.domains.facility.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `room_id` | `roomId` | Room Id | yes |
| `feed_type` | `feedType` | Feed Type | no |
| `voltage` | `voltage` | Voltage | no |
| `amperage` | `amperage` | Amperage | no |
| `phase` | `phase` | Phase | no |
| `breaker_label` | `breakerLabel` | Breaker Label | no |
| `redundancy_group` | `redundancyGroup` | Redundancy Group | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/power-feeds` search
* `GET /api/v1/power-feeds/meta` field catalogue
* `POST /api/v1/power-feeds/reconcile?apply=false` reconcile external records
* `GET /api/v1/power-feeds/export` CSV
