# Dns Record feature

Route prefix `/ipam/dns-records` - folder `src/features/ipam/dns-record`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `zoneName` | Zone Name | yes | `s` |
| `recordType` | Record Type | no | `e` |
| `recordValue` | Record Value | yes | `s` |
| `ttl` | Ttl | no | `i` |
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
* Zone Name
* Record Type
* Record Value
* Ttl
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `DnsRecordListPage` - table, filters, bulk actions, CSV export
* `DnsRecordDetailPage` - read only view with status buttons
* `DnsRecordCreatePage` / `DnsRecordEditPage` - shared `DnsRecordForm`
