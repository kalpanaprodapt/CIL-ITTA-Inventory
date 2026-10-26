# Virtual Machine feature

Route prefix `/virtual/virtual-machines` - folder `src/features/virtual/virtual-machine`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `hypervisorId` | Hypervisor Id | yes | `u` |
| `vmName` | Vm Name | yes | `s` |
| `vcpu` | Vcpu | yes | `i` |
| `memoryGb` | Memory Gb | yes | `i` |
| `diskGb` | Disk Gb | no | `i` |
| `powerState` | Power State | no | `e` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Hypervisor Id
* Vm Name
* Vcpu
* Memory Gb
* Disk Gb
* Power State
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `VirtualMachineListPage` - table, filters, bulk actions, CSV export
* `VirtualMachineDetailPage` - read only view with status buttons
* `VirtualMachineCreatePage` / `VirtualMachineEditPage` - shared `VirtualMachineForm`
