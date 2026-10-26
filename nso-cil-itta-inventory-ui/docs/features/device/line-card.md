# Line Card feature

Route prefix `/device/line-cards` - folder `src/features/device/line-card`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `chassisId` | Chassis Id | yes | `u` |
| `slotNumber` | Slot Number | yes | `i` |
| `cardType` | Card Type | no | `s` |
| `portCount` | Port Count | no | `i` |
| `serialNumber` | Serial Number | no | `s` |
| `firmware` | Firmware | no | `s` |
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
* Chassis Id
* Slot Number
* Card Type
* Port Count
* Serial Number
* Firmware
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `LineCardListPage` - table, filters, bulk actions, CSV export
* `LineCardDetailPage` - read only view with status buttons
* `LineCardCreatePage` / `LineCardEditPage` - shared `LineCardForm`
