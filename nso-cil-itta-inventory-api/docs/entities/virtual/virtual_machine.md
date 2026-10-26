# Virtual Machine

Domain: **Virtual and Cloud** (`virtual`) - ASGI app `itta_api.domains.virtual.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `hypervisor_id` | `hypervisorId` | Hypervisor Id | yes |
| `vm_name` | `vmName` | Vm Name | yes |
| `vcpu` | `vcpu` | Vcpu | yes |
| `memory_gb` | `memoryGb` | Memory Gb | yes |
| `disk_gb` | `diskGb` | Disk Gb | no |
| `power_state` | `powerState` | Power State | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/virtual-machines` search
* `GET /api/v1/virtual-machines/meta` field catalogue
* `POST /api/v1/virtual-machines/reconcile?apply=false` reconcile external records
* `GET /api/v1/virtual-machines/export` CSV
