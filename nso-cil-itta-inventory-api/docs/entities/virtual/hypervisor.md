# Hypervisor

Domain: **Virtual and Cloud** (`virtual`) - ASGI app `itta_api.domains.virtual.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `host_device_id` | `hostDeviceId` | Host Device Id | yes |
| `hypervisor_type` | `hypervisorType` | Hypervisor Type | no |
| `hv_version` | `hvVersion` | Hv Version | no |
| `vcpu_capacity` | `vcpuCapacity` | Vcpu Capacity | no |
| `memory_gb_capacity` | `memoryGbCapacity` | Memory Gb Capacity | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/hypervisors` search
* `GET /api/v1/hypervisors/meta` field catalogue
* `POST /api/v1/hypervisors/reconcile?apply=false` reconcile external records
* `GET /api/v1/hypervisors/export` CSV
