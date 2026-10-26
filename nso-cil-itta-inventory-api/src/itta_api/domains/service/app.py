"""FastAPI application for the Services domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.service.service_catalog_item.client import ServiceCatalogItemClient
from itta_api.domains.service.service_catalog_item.service import ServiceCatalogItemService
from itta_api.domains.service.service_instance.client import ServiceInstanceClient
from itta_api.domains.service.service_instance.service import ServiceInstanceService
from itta_api.domains.service.service_order.client import ServiceOrderClient
from itta_api.domains.service.service_order.service import ServiceOrderService
from itta_api.domains.service.service_dependency.client import ServiceDependencyClient
from itta_api.domains.service.service_dependency.service import ServiceDependencyService
from itta_api.domains.service.service_endpoint.client import ServiceEndpointClient
from itta_api.domains.service.service_endpoint.service import ServiceEndpointService
from itta_api.domains.service.service_catalog_item.router import router as service_catalog_item_router
from itta_api.domains.service.service_instance.router import router as service_instance_router
from itta_api.domains.service.service_order.router import router as service_order_router
from itta_api.domains.service.service_dependency.router import router as service_dependency_router
from itta_api.domains.service.service_endpoint.router import router as service_endpoint_router

DOMAIN = "service"
UPSTREAM_ENV = "INVENTORY_SERVICE_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("service_catalog_item", lambda up, cache: ServiceCatalogItemService(ServiceCatalogItemClient(up), cache))
    registry.register("service_instance", lambda up, cache: ServiceInstanceService(ServiceInstanceClient(up), cache))
    registry.register("service_order", lambda up, cache: ServiceOrderService(ServiceOrderClient(up), cache))
    registry.register("service_dependency", lambda up, cache: ServiceDependencyService(ServiceDependencyClient(up), cache))
    registry.register("service_endpoint", lambda up, cache: ServiceEndpointService(ServiceEndpointClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Services", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(service_catalog_item_router)
    app.include_router(service_instance_router)
    app.include_router(service_order_router)
    app.include_router(service_dependency_router)
    app.include_router(service_endpoint_router)
    return app


app = create()
