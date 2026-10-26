# Vnf

Domain: **Virtual and Cloud** (`virtual`) - ASGI app `itta_api.domains.virtual.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vnf_name` | `vnfName` | Vnf Name | yes |
| `vnf_type` | `vnfType` | Vnf Type | yes |
| `flavor` | `flavor` | Flavor | no |
| `vcpu` | `vcpu` | Vcpu | no |
| `memory_gb` | `memoryGb` | Memory Gb | no |
| `instance_count` | `instanceCount` | Instance Count | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/vnfs` search
* `GET /api/v1/vnfs/meta` field catalogue
* `POST /api/v1/vnfs/reconcile?apply=false` reconcile external records
* `GET /api/v1/vnfs/export` CSV
