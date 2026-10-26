# Compliance Exception feature

Route prefix `/compliance/compliance-exceptions` - folder `src/features/compliance/compliance-exception`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `ruleId` | Rule Id | yes | `u` |
| `targetRef` | Target Ref | yes | `s` |
| `justification` | Justification | yes | `t` |
| `approvedBy` | Approved By | no | `s` |
| `expiresOn` | Expires On | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Rule Id
* Target Ref
* Justification
* Approved By
* Expires On
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `ComplianceExceptionListPage` - table, filters, bulk actions, CSV export
* `ComplianceExceptionDetailPage` - read only view with status buttons
* `ComplianceExceptionCreatePage` / `ComplianceExceptionEditPage` - shared `ComplianceExceptionForm`
