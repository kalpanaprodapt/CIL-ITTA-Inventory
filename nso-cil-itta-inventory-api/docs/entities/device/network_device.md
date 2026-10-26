# Network Device

Domain: **Network Device** (`device`) - ASGI app `itta_api.domains.device.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `hostname` | `hostname` | Hostname | yes |
| `device_role` | `deviceRole` | Device Role | no |
| `mgmt_ip` | `mgmtIp` | Mgmt Ip | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `model_id` | `modelId` | Model Id | no |
| `rack_unit_id` | `rackUnitId` | Rack Unit Id | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/network-devices` search
* `GET /api/v1/network-devices/meta` field catalogue
* `POST /api/v1/network-devices/reconcile?apply=false` reconcile external records
* `GET /api/v1/network-devices/export` CSV
