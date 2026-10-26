"""FastAPI application for the Optical domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.optical.optical_channel.client import OpticalChannelClient
from itta_api.domains.optical.optical_channel.service import OpticalChannelService
from itta_api.domains.optical.wdm_system.client import WdmSystemClient
from itta_api.domains.optical.wdm_system.service import WdmSystemService
from itta_api.domains.optical.otn_link.client import OtnLinkClient
from itta_api.domains.optical.otn_link.service import OtnLinkService
from itta_api.domains.optical.amplifier.client import AmplifierClient
from itta_api.domains.optical.amplifier.service import AmplifierService
from itta_api.domains.optical.roadm.client import RoadmClient
from itta_api.domains.optical.roadm.service import RoadmService
from itta_api.domains.optical.optical_channel.router import router as optical_channel_router
from itta_api.domains.optical.wdm_system.router import router as wdm_system_router
from itta_api.domains.optical.otn_link.router import router as otn_link_router
from itta_api.domains.optical.amplifier.router import router as amplifier_router
from itta_api.domains.optical.roadm.router import router as roadm_router

DOMAIN = "optical"
UPSTREAM_ENV = "INVENTORY_OPTICAL_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("optical_channel", lambda up, cache: OpticalChannelService(OpticalChannelClient(up), cache))
    registry.register("wdm_system", lambda up, cache: WdmSystemService(WdmSystemClient(up), cache))
    registry.register("otn_link", lambda up, cache: OtnLinkService(OtnLinkClient(up), cache))
    registry.register("amplifier", lambda up, cache: AmplifierService(AmplifierClient(up), cache))
    registry.register("roadm", lambda up, cache: RoadmService(RoadmClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Optical", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(optical_channel_router)
    app.include_router(wdm_system_router)
    app.include_router(otn_link_router)
    app.include_router(amplifier_router)
    app.include_router(roadm_router)
    return app


app = create()
