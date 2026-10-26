# Policy Document feature

Route prefix `/compliance/policy-documents` - folder `src/features/compliance/policy-document`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `title` | Title | yes | `s` |
| `docVersion` | Doc Version | yes | `s` |
| `effectiveDate` | Effective Date | yes | `D` |
| `owner` | Owner | no | `s` |
| `documentUrl` | Document Url | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Title
* Doc Version
* Effective Date
* Owner
* Document Url
* Lifecycle Stage
* Source System
* Region
* Criticality
* Cost Center

## Pages

* `PolicyDocumentListPage` - table, filters, bulk actions, CSV export
* `PolicyDocumentDetailPage` - read only view with status buttons
* `PolicyDocumentCreatePage` / `PolicyDocumentEditPage` - shared `PolicyDocumentForm`
