# Splice

Domain: **Cabling** (`cabling`) - ASGI app `itta_api.domains.cabling.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `enclosure_id` | `enclosureId` | Enclosure Id | yes |
| `a_strand_id` | `aStrandId` | A Strand Id | no |
| `z_strand_id` | `zStrandId` | Z Strand Id | no |
| `loss_db` | `lossDb` | Loss Db | no |
| `spliced_on` | `splicedOn` | Spliced On | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/splices` search
* `GET /api/v1/splices/meta` field catalogue
* `POST /api/v1/splices/reconcile?apply=false` reconcile external records
* `GET /api/v1/splices/export` CSV
