"""FastAPI application for the Circuits domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.circuit.circuit.client import CircuitClient
from itta_api.domains.circuit.circuit.service import CircuitService
from itta_api.domains.circuit.circuit_segment.client import CircuitSegmentClient
from itta_api.domains.circuit.circuit_segment.service import CircuitSegmentService
from itta_api.domains.circuit.circuit_provider.client import CircuitProviderClient
from itta_api.domains.circuit.circuit_provider.service import CircuitProviderService
from itta_api.domains.circuit.circuit_termination.client import CircuitTerminationClient
from itta_api.domains.circuit.circuit_termination.service import CircuitTerminationService
from itta_api.domains.circuit.circuit_order.client import CircuitOrderClient
from itta_api.domains.circuit.circuit_order.service import CircuitOrderService
from itta_api.domains.circuit.circuit.router import router as circuit_router
from itta_api.domains.circuit.circuit_segment.router import router as circuit_segment_router
from itta_api.domains.circuit.circuit_provider.router import router as circuit_provider_router
from itta_api.domains.circuit.circuit_termination.router import router as circuit_termination_router
from itta_api.domains.circuit.circuit_order.router import router as circuit_order_router

DOMAIN = "circuit"
UPSTREAM_ENV = "INVENTORY_CIRCUIT_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("circuit", lambda up, cache: CircuitService(CircuitClient(up), cache))
    registry.register("circuit_segment", lambda up, cache: CircuitSegmentService(CircuitSegmentClient(up), cache))
    registry.register("circuit_provider", lambda up, cache: CircuitProviderService(CircuitProviderClient(up), cache))
    registry.register("circuit_termination", lambda up, cache: CircuitTerminationService(CircuitTerminationClient(up), cache))
    registry.register("circuit_order", lambda up, cache: CircuitOrderService(CircuitOrderClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Circuits", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(circuit_router)
    app.include_router(circuit_segment_router)
    app.include_router(circuit_provider_router)
    app.include_router(circuit_termination_router)
    app.include_router(circuit_order_router)
    return app


app = create()
