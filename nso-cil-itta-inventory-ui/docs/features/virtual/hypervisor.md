# Hypervisor feature

Route prefix `/virtual/hypervisors` - folder `src/features/virtual/hypervisor`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `hostDeviceId` | Host Device Id | yes | `u` |
| `hypervisorType` | Hypervisor Type | no | `e` |
| `hvVersion` | Hv Version | no | `s` |
| `vcpuCapacity` | Vcpu Capacity | no | `i` |
| `memoryGbCapacity` | Memory Gb Capacity | no | `i` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Host Device Id
* Hypervisor Type
* Hv Version
* Vcpu Capacity
* Memory Gb Capacity
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `HypervisorListPage` - table, filters, bulk actions, CSV export
* `HypervisorDetailPage` - read only view with status buttons
* `HypervisorCreatePage` / `HypervisorEditPage` - shared `HypervisorForm`
