"""FastAPI application for the Radio and Mobile domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.radio.radio_unit.client import RadioUnitClient
from itta_api.domains.radio.radio_unit.service import RadioUnitService
from itta_api.domains.radio.base_station.client import BaseStationClient
from itta_api.domains.radio.base_station.service import BaseStationService
from itta_api.domains.radio.antenna.client import AntennaClient
from itta_api.domains.radio.antenna.service import AntennaService
from itta_api.domains.radio.sector.client import SectorClient
from itta_api.domains.radio.sector.service import SectorService
from itta_api.domains.radio.backhaul_link.client import BackhaulLinkClient
from itta_api.domains.radio.backhaul_link.service import BackhaulLinkService
from itta_api.domains.radio.radio_unit.router import router as radio_unit_router
from itta_api.domains.radio.base_station.router import router as base_station_router
from itta_api.domains.radio.antenna.router import router as antenna_router
from itta_api.domains.radio.sector.router import router as sector_router
from itta_api.domains.radio.backhaul_link.router import router as backhaul_link_router

DOMAIN = "radio"
UPSTREAM_ENV = "INVENTORY_RADIO_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("radio_unit", lambda up, cache: RadioUnitService(RadioUnitClient(up), cache))
    registry.register("base_station", lambda up, cache: BaseStationService(BaseStationClient(up), cache))
    registry.register("antenna", lambda up, cache: AntennaService(AntennaClient(up), cache))
    registry.register("sector", lambda up, cache: SectorService(SectorClient(up), cache))
    registry.register("backhaul_link", lambda up, cache: BackhaulLinkService(BackhaulLinkClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Radio and Mobile", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(radio_unit_router)
    app.include_router(base_station_router)
    app.include_router(antenna_router)
    app.include_router(sector_router)
    app.include_router(backhaul_link_router)
    return app


app = create()
