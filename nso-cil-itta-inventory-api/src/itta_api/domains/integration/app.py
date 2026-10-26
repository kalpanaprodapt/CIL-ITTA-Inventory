"""FastAPI application for the Integration Layer domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.integration.integration_endpoint.client import IntegrationEndpointClient
from itta_api.domains.integration.integration_endpoint.service import IntegrationEndpointService
from itta_api.domains.integration.sync_job.client import SyncJobClient
from itta_api.domains.integration.sync_job.service import SyncJobService
from itta_api.domains.integration.sync_mapping.client import SyncMappingClient
from itta_api.domains.integration.sync_mapping.service import SyncMappingService
from itta_api.domains.integration.import_batch.client import ImportBatchClient
from itta_api.domains.integration.import_batch.service import ImportBatchService
from itta_api.domains.integration.webhook_subscription.client import WebhookSubscriptionClient
from itta_api.domains.integration.webhook_subscription.service import WebhookSubscriptionService
from itta_api.domains.integration.integration_endpoint.router import router as integration_endpoint_router
from itta_api.domains.integration.sync_job.router import router as sync_job_router
from itta_api.domains.integration.sync_mapping.router import router as sync_mapping_router
from itta_api.domains.integration.import_batch.router import router as import_batch_router
from itta_api.domains.integration.webhook_subscription.router import router as webhook_subscription_router

DOMAIN = "integration"
UPSTREAM_ENV = "INVENTORY_INTEGRATION_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("integration_endpoint", lambda up, cache: IntegrationEndpointService(IntegrationEndpointClient(up), cache))
    registry.register("sync_job", lambda up, cache: SyncJobService(SyncJobClient(up), cache))
    registry.register("sync_mapping", lambda up, cache: SyncMappingService(SyncMappingClient(up), cache))
    registry.register("import_batch", lambda up, cache: ImportBatchService(ImportBatchClient(up), cache))
    registry.register("webhook_subscription", lambda up, cache: WebhookSubscriptionService(WebhookSubscriptionClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Integration Layer", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(integration_endpoint_router)
    app.include_router(sync_job_router)
    app.include_router(sync_mapping_router)
    app.include_router(import_batch_router)
    app.include_router(webhook_subscription_router)
    return app


app = create()
