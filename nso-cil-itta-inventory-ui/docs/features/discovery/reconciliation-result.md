# Reconciliation Result feature

Route prefix `/discovery/reconciliation-results` - folder `src/features/discovery/reconciliation-result`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `jobId` | Job Id | yes | `u` |
| `ruleId` | Rule Id | no | `u` |
| `outcome` | Outcome | no | `e` |
| `diffSummary` | Diff Summary | no | `t` |
| `resolvedBy` | Resolved By | no | `s` |
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
* Job Id
* Rule Id
* Outcome
* Diff Summary
* Resolved By
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `ReconciliationResultListPage` - table, filters, bulk actions, CSV export
* `ReconciliationResultDetailPage` - read only view with status buttons
* `ReconciliationResultCreatePage` / `ReconciliationResultEditPage` - shared `ReconciliationResultForm`
