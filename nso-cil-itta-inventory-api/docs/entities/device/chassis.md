# Chassis

Domain: **Network Device** (`device`) - ASGI app `itta_api.domains.device.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `device_id` | `deviceId` | Device Id | yes |
| `slot_count` | `slotCount` | Slot Count | yes |
| `serial_number` | `serialNumber` | Serial Number | no |
| `backplane_gbps` | `backplaneGbps` | Backplane Gbps | no |
| `redundancy_mode` | `redundancyMode` | Redundancy Mode | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/chassises` search
* `GET /api/v1/chassises/meta` field catalogue
* `POST /api/v1/chassises/reconcile?apply=false` reconcile external records
* `GET /api/v1/chassises/export` CSV
