# Threshold Policy feature

Route prefix `/capacity/threshold-policies` - folder `src/features/capacity/threshold-policy`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `policyName` | Policy Name | yes | `s` |
| `metric` | Metric | yes | `s` |
| `warnPct` | Warn Pct | yes | `d` |
| `critPct` | Crit Pct | yes | `d` |
| `evaluationWindowMinutes` | Evaluation Window Minutes | no | `i` |
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
* Policy Name
* Metric
* Warn Pct
* Crit Pct
* Evaluation Window Minutes
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `ThresholdPolicyListPage` - table, filters, bulk actions, CSV export
* `ThresholdPolicyDetailPage` - read only view with status buttons
* `ThresholdPolicyCreatePage` / `ThresholdPolicyEditPage` - shared `ThresholdPolicyForm`
