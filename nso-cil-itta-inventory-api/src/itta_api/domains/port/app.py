"""FastAPI application for the Port and Interface domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.port.port.client import PortClient
from itta_api.domains.port.port.service import PortService
from itta_api.domains.port.interface.client import InterfaceClient
from itta_api.domains.port.interface.service import InterfaceService
from itta_api.domains.port.sub_interface.client import SubInterfaceClient
from itta_api.domains.port.sub_interface.service import SubInterfaceService
from itta_api.domains.port.lag_group.client import LagGroupClient
from itta_api.domains.port.lag_group.service import LagGroupService
from itta_api.domains.port.transceiver.client import TransceiverClient
from itta_api.domains.port.transceiver.service import TransceiverService
from itta_api.domains.port.port.router import router as port_router
from itta_api.domains.port.interface.router import router as interface_router
from itta_api.domains.port.sub_interface.router import router as sub_interface_router
from itta_api.domains.port.lag_group.router import router as lag_group_router
from itta_api.domains.port.transceiver.router import router as transceiver_router

DOMAIN = "port"
UPSTREAM_ENV = "INVENTORY_PORT_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("port", lambda up, cache: PortService(PortClient(up), cache))
    registry.register("interface", lambda up, cache: InterfaceService(InterfaceClient(up), cache))
    registry.register("sub_interface", lambda up, cache: SubInterfaceService(SubInterfaceClient(up), cache))
    registry.register("lag_group", lambda up, cache: LagGroupService(LagGroupClient(up), cache))
    registry.register("transceiver", lambda up, cache: TransceiverService(TransceiverClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Port and Interface", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(port_router)
    app.include_router(interface_router)
    app.include_router(sub_interface_router)
    app.include_router(lag_group_router)
    app.include_router(transceiver_router)
    return app


app = create()
