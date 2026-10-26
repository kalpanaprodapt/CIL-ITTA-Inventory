"""FastAPI application for the Discovery and Reconciliation domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.discovery.discovery_job.client import DiscoveryJobClient
from itta_api.domains.discovery.discovery_job.service import DiscoveryJobService
from itta_api.domains.discovery.discovery_profile.client import DiscoveryProfileClient
from itta_api.domains.discovery.discovery_profile.service import DiscoveryProfileService
from itta_api.domains.discovery.discovered_asset.client import DiscoveredAssetClient
from itta_api.domains.discovery.discovered_asset.service import DiscoveredAssetService
from itta_api.domains.discovery.reconciliation_rule.client import ReconciliationRuleClient
from itta_api.domains.discovery.reconciliation_rule.service import ReconciliationRuleService
from itta_api.domains.discovery.reconciliation_result.client import ReconciliationResultClient
from itta_api.domains.discovery.reconciliation_result.service import ReconciliationResultService
from itta_api.domains.discovery.discovery_job.router import router as discovery_job_router
from itta_api.domains.discovery.discovery_profile.router import router as discovery_profile_router
from itta_api.domains.discovery.discovered_asset.router import router as discovered_asset_router
from itta_api.domains.discovery.reconciliation_rule.router import router as reconciliation_rule_router
from itta_api.domains.discovery.reconciliation_result.router import router as reconciliation_result_router

DOMAIN = "discovery"
UPSTREAM_ENV = "INVENTORY_DISCOVERY_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("discovery_job", lambda up, cache: DiscoveryJobService(DiscoveryJobClient(up), cache))
    registry.register("discovery_profile", lambda up, cache: DiscoveryProfileService(DiscoveryProfileClient(up), cache))
    registry.register("discovered_asset", lambda up, cache: DiscoveredAssetService(DiscoveredAssetClient(up), cache))
    registry.register("reconciliation_rule", lambda up, cache: ReconciliationRuleService(ReconciliationRuleClient(up), cache))
    registry.register("reconciliation_result", lambda up, cache: ReconciliationResultService(ReconciliationResultClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Discovery and Reconciliation", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(discovery_job_router)
    app.include_router(discovery_profile_router)
    app.include_router(discovered_asset_router)
    app.include_router(reconciliation_rule_router)
    app.include_router(reconciliation_result_router)
    return app


app = create()
