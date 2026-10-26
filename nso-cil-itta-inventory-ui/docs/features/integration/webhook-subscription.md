# Webhook Subscription feature

Route prefix `/integration/webhook-subscriptions` - folder `src/features/integration/webhook-subscription`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `callbackUrl` | Callback Url | yes | `s` |
| `eventTypes` | Event Types | yes | `s` |
| `secretRef` | Secret Ref | no | `s` |
| `active` | Active | no | `b` |
| `retryLimit` | Retry Limit | no | `i` |
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
* Callback Url
* Event Types
* Secret Ref
* Active
* Retry Limit
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `WebhookSubscriptionListPage` - table, filters, bulk actions, CSV export
* `WebhookSubscriptionDetailPage` - read only view with status buttons
* `WebhookSubscriptionCreatePage` / `WebhookSubscriptionEditPage` - shared `WebhookSubscriptionForm`
