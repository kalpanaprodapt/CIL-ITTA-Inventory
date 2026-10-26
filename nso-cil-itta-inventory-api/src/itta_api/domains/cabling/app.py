"""FastAPI application for the Cabling domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.cabling.cable.client import CableClient
from itta_api.domains.cabling.cable.service import CableService
from itta_api.domains.cabling.patch_panel.client import PatchPanelClient
from itta_api.domains.cabling.patch_panel.service import PatchPanelService
from itta_api.domains.cabling.patch_port.client import PatchPortClient
from itta_api.domains.cabling.patch_port.service import PatchPortService
from itta_api.domains.cabling.splice.client import SpliceClient
from itta_api.domains.cabling.splice.service import SpliceService
from itta_api.domains.cabling.fiber_strand.client import FiberStrandClient
from itta_api.domains.cabling.fiber_strand.service import FiberStrandService
from itta_api.domains.cabling.cable.router import router as cable_router
from itta_api.domains.cabling.patch_panel.router import router as patch_panel_router
from itta_api.domains.cabling.patch_port.router import router as patch_port_router
from itta_api.domains.cabling.splice.router import router as splice_router
from itta_api.domains.cabling.fiber_strand.router import router as fiber_strand_router

DOMAIN = "cabling"
UPSTREAM_ENV = "INVENTORY_CABLING_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("cable", lambda up, cache: CableService(CableClient(up), cache))
    registry.register("patch_panel", lambda up, cache: PatchPanelService(PatchPanelClient(up), cache))
    registry.register("patch_port", lambda up, cache: PatchPortService(PatchPortClient(up), cache))
    registry.register("splice", lambda up, cache: SpliceService(SpliceClient(up), cache))
    registry.register("fiber_strand", lambda up, cache: FiberStrandService(FiberStrandClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Cabling", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(cable_router)
    app.include_router(patch_panel_router)
    app.include_router(patch_port_router)
    app.include_router(splice_router)
    app.include_router(fiber_strand_router)
    return app


app = create()
