# Suppression Rule feature

Route prefix `/alarm/suppression-rules` - folder `src/features/alarm/suppression-rule`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `ruleName` | Rule Name | yes | `s` |
| `matchExpression` | Match Expression | yes | `t` |
| `startsAt` | Starts At | no | `T` |
| `endsAt` | Ends At | no | `T` |
| `reason` | Reason | no | `s` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Rule Name
* Match Expression
* Starts At
* Ends At
* Reason
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `SuppressionRuleListPage` - table, filters, bulk actions, CSV export
* `SuppressionRuleDetailPage` - read only view with status buttons
* `SuppressionRuleCreatePage` / `SuppressionRuleEditPage` - shared `SuppressionRuleForm`
