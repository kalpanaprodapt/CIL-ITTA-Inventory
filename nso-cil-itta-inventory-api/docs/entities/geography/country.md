# Country

Domain: **Geography** (`geography`) - ASGI app `itta_api.domains.geography.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `iso_code` | `isoCode` | Iso Code | yes |
| `iso_numeric` | `isoNumeric` | Iso Numeric | no |
| `dial_code` | `dialCode` | Dial Code | no |
| `currency` | `currency` | Currency | no |
| `regulatory_body` | `regulatoryBody` | Regulatory Body | no |
| `timezone` | `timezone` | Timezone | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/countries` search
* `GET /api/v1/countries/meta` field catalogue
* `POST /api/v1/countries/reconcile?apply=false` reconcile external records
* `GET /api/v1/countries/export` CSV
