# Runbook - Compliance and Audit

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-compliance-service | Spring Boot | 8080 | Grafana `itta-compliance` |
| inventory-compliance-api | FastAPI | 8000 | Grafana `itta-compliance` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-compliance-service inventory-compliance-api
kubectl -n itta-inventory-prod logs deploy/inventory-compliance-service --tail=200
curl -fsS https://api-compliance.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Compliance Rule

* API: `/api/v1/compliance-rules` on `inventory-compliance-service`, facade on `inventory-compliance-api`
* Table: `inv_compliance_rule` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.compliance.compliancerule`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh compliance ComplianceRule`

### Compliance Check

* API: `/api/v1/compliance-checks` on `inventory-compliance-service`, facade on `inventory-compliance-api`
* Table: `inv_compliance_check` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.compliance.compliancecheck`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh compliance ComplianceCheck`

### Audit Record

* API: `/api/v1/audit-records` on `inventory-compliance-service`, facade on `inventory-compliance-api`
* Table: `inv_audit_record` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.compliance.auditrecord`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh compliance AuditRecord`

### Compliance Exception

* API: `/api/v1/compliance-exceptions` on `inventory-compliance-service`, facade on `inventory-compliance-api`
* Table: `inv_compliance_exception` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.compliance.complianceexception`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh compliance ComplianceException`

### Policy Document

* API: `/api/v1/policy-documents` on `inventory-compliance-service`, facade on `inventory-compliance-api`
* Table: `inv_policy_document` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.compliance.policydocument`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh compliance PolicyDocument`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-compliance-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-compliance-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
