# Amplifier

Domain: **Optical** (`optical`) - ASGI app `itta_api.domains.optical.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `system_id` | `systemId` | System Id | yes |
| `amp_type` | `ampType` | Amp Type | no |
| `gain_db` | `gainDb` | Gain Db | yes |
| `output_power_dbm` | `outputPowerDbm` | Output Power Dbm | no |
| `noise_figure_db` | `noiseFigureDb` | Noise Figure Db | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/amplifiers` search
* `GET /api/v1/amplifiers/meta` field catalogue
* `POST /api/v1/amplifiers/reconcile?apply=false` reconcile external records
* `GET /api/v1/amplifiers/export` CSV
