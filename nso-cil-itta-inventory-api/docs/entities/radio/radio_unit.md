# Radio Unit

Domain: **Radio and Mobile** (`radio`) - ASGI app `itta_api.domains.radio.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `site_id` | `siteId` | Site Id | yes |
| `unit_type` | `unitType` | Unit Type | no |
| `band_mhz` | `bandMhz` | Band Mhz | no |
| `tx_power_dbm` | `txPowerDbm` | Tx Power Dbm | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/radio-units` search
* `GET /api/v1/radio-units/meta` field catalogue
* `POST /api/v1/radio-units/reconcile?apply=false` reconcile external records
* `GET /api/v1/radio-units/export` CSV
