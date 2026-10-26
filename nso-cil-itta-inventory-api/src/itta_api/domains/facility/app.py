"""FastAPI application for the Facility domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.facility.floor.client import FloorClient
from itta_api.domains.facility.floor.service import FloorService
from itta_api.domains.facility.room.client import RoomClient
from itta_api.domains.facility.room.service import RoomService
from itta_api.domains.facility.rack.client import RackClient
from itta_api.domains.facility.rack.service import RackService
from itta_api.domains.facility.rack_unit.client import RackUnitClient
from itta_api.domains.facility.rack_unit.service import RackUnitService
from itta_api.domains.facility.power_feed.client import PowerFeedClient
from itta_api.domains.facility.power_feed.service import PowerFeedService
from itta_api.domains.facility.floor.router import router as floor_router
from itta_api.domains.facility.room.router import router as room_router
from itta_api.domains.facility.rack.router import router as rack_router
from itta_api.domains.facility.rack_unit.router import router as rack_unit_router
from itta_api.domains.facility.power_feed.router import router as power_feed_router

DOMAIN = "facility"
UPSTREAM_ENV = "INVENTORY_FACILITY_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("floor", lambda up, cache: FloorService(FloorClient(up), cache))
    registry.register("room", lambda up, cache: RoomService(RoomClient(up), cache))
    registry.register("rack", lambda up, cache: RackService(RackClient(up), cache))
    registry.register("rack_unit", lambda up, cache: RackUnitService(RackUnitClient(up), cache))
    registry.register("power_feed", lambda up, cache: PowerFeedService(PowerFeedClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Facility", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(floor_router)
    app.include_router(room_router)
    app.include_router(rack_router)
    app.include_router(rack_unit_router)
    app.include_router(power_feed_router)
    return app


app = create()
