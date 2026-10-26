# Transceiver

Domain: **Port and Interface** (`port`) - ASGI app `itta_api.domains.port.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `port_id` | `portId` | Port Id | no |
| `form_factor` | `formFactor` | Form Factor | no |
| `wavelength_nm` | `wavelengthNm` | Wavelength Nm | no |
| `reach_km` | `reachKm` | Reach Km | no |
| `serial_number` | `serialNumber` | Serial Number | no |
| `vendor_part` | `vendorPart` | Vendor Part | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/transceivers` search
* `GET /api/v1/transceivers/meta` field catalogue
* `POST /api/v1/transceivers/reconcile?apply=false` reconcile external records
* `GET /api/v1/transceivers/export` CSV
