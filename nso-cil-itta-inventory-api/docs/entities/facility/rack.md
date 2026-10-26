# Rack

Domain: **Facility** (`facility`) - ASGI app `itta_api.domains.facility.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `room_id` | `roomId` | Room Id | yes |
| `rack_units` | `rackUnits` | Rack Units | yes |
| `width_mm` | `widthMm` | Width Mm | no |
| `depth_mm` | `depthMm` | Depth Mm | no |
| `max_weight_kg` | `maxWeightKg` | Max Weight Kg | no |
| `row_label` | `rowLabel` | Row Label | no |
| `position` | `position` | Position | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/racks` search
* `GET /api/v1/racks/meta` field catalogue
* `POST /api/v1/racks/reconcile?apply=false` reconcile external records
* `GET /api/v1/racks/export` CSV
