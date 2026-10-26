# Vrf Instance

Domain: **IP Management** (`ipam`) - ASGI app `itta_api.domains.ipam.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vrf_name` | `vrfName` | Vrf Name | yes |
| `route_distinguisher` | `routeDistinguisher` | Route Distinguisher | yes |
| `import_targets` | `importTargets` | Import Targets | no |
| `export_targets` | `exportTargets` | Export Targets | no |
| `device_id` | `deviceId` | Device Id | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/vrf-instances` search
* `GET /api/v1/vrf-instances/meta` field catalogue
* `POST /api/v1/vrf-instances/reconcile?apply=false` reconcile external records
* `GET /api/v1/vrf-instances/export` CSV
