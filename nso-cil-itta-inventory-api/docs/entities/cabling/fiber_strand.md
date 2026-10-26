# Fiber Strand

Domain: **Cabling** (`cabling`) - ASGI app `itta_api.domains.cabling.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `cable_id` | `cableId` | Cable Id | yes |
| `strand_number` | `strandNumber` | Strand Number | yes |
| `buffer_tube` | `bufferTube` | Buffer Tube | no |
| `tube_color` | `tubeColor` | Tube Color | no |
| `attenuation_db` | `attenuationDb` | Attenuation Db | no |
| `in_use` | `inUse` | In Use | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/fiber-strands` search
* `GET /api/v1/fiber-strands/meta` field catalogue
* `POST /api/v1/fiber-strands/reconcile?apply=false` reconcile external records
* `GET /api/v1/fiber-strands/export` CSV
