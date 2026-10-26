# Audit Record feature

Route prefix `/compliance/audit-records` - folder `src/features/compliance/audit-record`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `actor` | Actor | yes | `s` |
| `action` | Action | yes | `s` |
| `entityRef` | Entity Ref | yes | `s` |
| `occurredAt` | Occurred At | yes | `T` |
| `beforeState` | Before State | no | `t` |
| `afterState` | After State | no | `t` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Actor
* Action
* Entity Ref
* Occurred At
* Before State
* After State
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `AuditRecordListPage` - table, filters, bulk actions, CSV export
* `AuditRecordDetailPage` - read only view with status buttons
* `AuditRecordCreatePage` / `AuditRecordEditPage` - shared `AuditRecordForm`
