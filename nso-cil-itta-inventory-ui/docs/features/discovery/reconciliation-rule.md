# Reconciliation Rule feature

Route prefix `/discovery/reconciliation-rules` - folder `src/features/discovery/reconciliation-rule`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `ruleName` | Rule Name | yes | `s` |
| `matchKey` | Match Key | yes | `s` |
| `action` | Action | no | `e` |
| `priority` | Priority | yes | `i` |
| `expression` | Expression | no | `t` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Rule Name
* Match Key
* Action
* Priority
* Expression
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `ReconciliationRuleListPage` - table, filters, bulk actions, CSV export
* `ReconciliationRuleDetailPage` - read only view with status buttons
* `ReconciliationRuleCreatePage` / `ReconciliationRuleEditPage` - shared `ReconciliationRuleForm`
