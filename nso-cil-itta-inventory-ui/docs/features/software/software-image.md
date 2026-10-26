# Software Image feature

Route prefix `/software/software-images` - folder `src/features/software/software-image`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vendorId` | Vendor Id | yes | `u` |
| `imageName` | Image Name | yes | `s` |
| `sizeMb` | Size Mb | no | `l` |
| `checksum` | Checksum | no | `s` |
| `platform` | Platform | no | `s` |
| `storagePath` | Storage Path | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Vendor Id
* Image Name
* Size Mb
* Checksum
* Platform
* Storage Path
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `SoftwareImageListPage` - table, filters, bulk actions, CSV export
* `SoftwareImageDetailPage` - read only view with status buttons
* `SoftwareImageCreatePage` / `SoftwareImageEditPage` - shared `SoftwareImageForm`
