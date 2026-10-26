# Compliance Rule feature

Route prefix `/compliance/compliance-rules` - folder `src/features/compliance/compliance-rule`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `ruleCode` | Rule Code | yes | `s` |
| `framework` | Framework | no | `e` |
| `severity` | Severity | no | `e` |
| `expression` | Expression | no | `t` |
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
* Rule Code
* Framework
* Severity
* Expression
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `ComplianceRuleListPage` - table, filters, bulk actions, CSV export
* `ComplianceRuleDetailPage` - read only view with status buttons
* `ComplianceRuleCreatePage` / `ComplianceRuleEditPage` - shared `ComplianceRuleForm`
