# Runbook - Alarms and Events

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-alarm-service | Spring Boot | 8080 | Grafana `itta-alarm` |
| inventory-alarm-api | FastAPI | 8000 | Grafana `itta-alarm` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-alarm-service inventory-alarm-api
kubectl -n itta-inventory-prod logs deploy/inventory-alarm-service --tail=200
curl -fsS https://api-alarm.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Alarm Definition

* API: `/api/v1/alarm-definitions` on `inventory-alarm-service`, facade on `inventory-alarm-api`
* Table: `inv_alarm_definition` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.alarm.alarmdefinition`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh alarm AlarmDefinition`

### Alarm Instance

* API: `/api/v1/alarm-instances` on `inventory-alarm-service`, facade on `inventory-alarm-api`
* Table: `inv_alarm_instance` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.alarm.alarminstance`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh alarm AlarmInstance`

### Event Source

* API: `/api/v1/event-sources` on `inventory-alarm-service`, facade on `inventory-alarm-api`
* Table: `inv_event_source` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.alarm.eventsource`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh alarm EventSource`

### Suppression Rule

* API: `/api/v1/suppression-rules` on `inventory-alarm-service`, facade on `inventory-alarm-api`
* Table: `inv_suppression_rule` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.alarm.suppressionrule`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh alarm SuppressionRule`

### Ticket Link

* API: `/api/v1/ticket-links` on `inventory-alarm-service`, facade on `inventory-alarm-api`
* Table: `inv_ticket_link` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.alarm.ticketlink`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh alarm TicketLink`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-alarm-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-alarm-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
