# Ticket Link

Domain: **Alarms and Events** (`alarm`) - ASGI app `itta_api.domains.alarm.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `alarm_id` | `alarmId` | Alarm Id | yes |
| `ticket_system` | `ticketSystem` | Ticket System | no |
| `ticket_number` | `ticketNumber` | Ticket Number | yes |
| `linked_at` | `linkedAt` | Linked At | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/ticket-links` search
* `GET /api/v1/ticket-links/meta` field catalogue
* `POST /api/v1/ticket-links/reconcile?apply=false` reconcile external records
* `GET /api/v1/ticket-links/export` CSV
