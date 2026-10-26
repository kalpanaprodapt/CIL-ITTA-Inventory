# Compliance Check feature

Route prefix `/compliance/compliance-checks` - folder `src/features/compliance/compliance-check`

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
| `checkedAt` | Checked At | yes | `T` |
| `result` | Result | no | `e` |
| `evidence` | Evidence | no | `t` |
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
* Rule Id
* Target Ref
* Checked At
* Result
* Evidence
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `ComplianceCheckListPage` - table, filters, bulk actions, CSV export
* `ComplianceCheckDetailPage` - read only view with status buttons
* `ComplianceCheckCreatePage` / `ComplianceCheckEditPage` - shared `ComplianceCheckForm`
