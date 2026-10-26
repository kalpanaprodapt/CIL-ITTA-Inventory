"""FastAPI application for the Compliance and Audit domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.compliance.compliance_rule.client import ComplianceRuleClient
from itta_api.domains.compliance.compliance_rule.service import ComplianceRuleService
from itta_api.domains.compliance.compliance_check.client import ComplianceCheckClient
from itta_api.domains.compliance.compliance_check.service import ComplianceCheckService
from itta_api.domains.compliance.audit_record.client import AuditRecordClient
from itta_api.domains.compliance.audit_record.service import AuditRecordService
from itta_api.domains.compliance.compliance_exception.client import ComplianceExceptionClient
from itta_api.domains.compliance.compliance_exception.service import ComplianceExceptionService
from itta_api.domains.compliance.policy_document.client import PolicyDocumentClient
from itta_api.domains.compliance.policy_document.service import PolicyDocumentService
from itta_api.domains.compliance.compliance_rule.router import router as compliance_rule_router
from itta_api.domains.compliance.compliance_check.router import router as compliance_check_router
from itta_api.domains.compliance.audit_record.router import router as audit_record_router
from itta_api.domains.compliance.compliance_exception.router import router as compliance_exception_router
from itta_api.domains.compliance.policy_document.router import router as policy_document_router

DOMAIN = "compliance"
UPSTREAM_ENV = "INVENTORY_COMPLIANCE_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("compliance_rule", lambda up, cache: ComplianceRuleService(ComplianceRuleClient(up), cache))
    registry.register("compliance_check", lambda up, cache: ComplianceCheckService(ComplianceCheckClient(up), cache))
    registry.register("audit_record", lambda up, cache: AuditRecordService(AuditRecordClient(up), cache))
    registry.register("compliance_exception", lambda up, cache: ComplianceExceptionService(ComplianceExceptionClient(up), cache))
    registry.register("policy_document", lambda up, cache: PolicyDocumentService(PolicyDocumentClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Compliance and Audit", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(compliance_rule_router)
    app.include_router(compliance_check_router)
    app.include_router(audit_record_router)
    app.include_router(compliance_exception_router)
    app.include_router(policy_document_router)
    return app


app = create()
