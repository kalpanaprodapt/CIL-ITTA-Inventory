# Service Dependency feature

Route prefix `/service/service-dependencies` - folder `src/features/service/service-dependency`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `serviceId` | Service Id | yes | `u` |
| `dependsOnId` | Depends On Id | yes | `u` |
| `dependencyType` | Dependency Type | no | `e` |
| `impactScore` | Impact Score | no | `d` |
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
* Service Id
* Depends On Id
* Dependency Type
* Impact Score
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `ServiceDependencyListPage` - table, filters, bulk actions, CSV export
* `ServiceDependencyDetailPage` - read only view with status buttons
* `ServiceDependencyCreatePage` / `ServiceDependencyEditPage` - shared `ServiceDependencyForm`
