# Country feature

Route prefix `/geography/countries` - folder `src/features/geography/country`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `isoCode` | Iso Code | yes | `s` |
| `isoNumeric` | Iso Numeric | no | `i` |
| `dialCode` | Dial Code | no | `s` |
| `currency` | Currency | no | `s` |
| `regulatoryBody` | Regulatory Body | no | `s` |
| `timezone` | Timezone | no | `s` |
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
* Iso Code
* Iso Numeric
* Dial Code
* Currency
* Regulatory Body
* Timezone
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `CountryListPage` - table, filters, bulk actions, CSV export
* `CountryDetailPage` - read only view with status buttons
* `CountryCreatePage` / `CountryEditPage` - shared `CountryForm`
