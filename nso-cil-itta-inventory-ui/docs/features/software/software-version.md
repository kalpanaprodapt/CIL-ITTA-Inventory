# Software Version feature

Route prefix `/software/software-versions` - folder `src/features/software/software-version`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `imageId` | Image Id | yes | `u` |
| `versionString` | Version String | yes | `s` |
| `releaseDate` | Release Date | no | `D` |
| `endOfSupport` | End Of Support | no | `D` |
| `releaseTrain` | Release Train | no | `e` |
| `cveCount` | Cve Count | no | `i` |
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
* Image Id
* Version String
* Release Date
* End Of Support
* Release Train
* Cve Count
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `SoftwareVersionListPage` - table, filters, bulk actions, CSV export
* `SoftwareVersionDetailPage` - read only view with status buttons
* `SoftwareVersionCreatePage` / `SoftwareVersionEditPage` - shared `SoftwareVersionForm`
