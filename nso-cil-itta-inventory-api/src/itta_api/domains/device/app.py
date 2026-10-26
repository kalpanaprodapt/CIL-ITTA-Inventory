"""FastAPI application for the Network Device domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.device.network_device.client import NetworkDeviceClient
from itta_api.domains.device.network_device.service import NetworkDeviceService
from itta_api.domains.device.chassis.client import ChassisClient
from itta_api.domains.device.chassis.service import ChassisService
from itta_api.domains.device.line_card.client import LineCardClient
from itta_api.domains.device.line_card.service import LineCardService
from itta_api.domains.device.power_supply.client import PowerSupplyClient
from itta_api.domains.device.power_supply.service import PowerSupplyService
from itta_api.domains.device.fan_tray.client import FanTrayClient
from itta_api.domains.device.fan_tray.service import FanTrayService
from itta_api.domains.device.network_device.router import router as network_device_router
from itta_api.domains.device.chassis.router import router as chassis_router
from itta_api.domains.device.line_card.router import router as line_card_router
from itta_api.domains.device.power_supply.router import router as power_supply_router
from itta_api.domains.device.fan_tray.router import router as fan_tray_router

DOMAIN = "device"
UPSTREAM_ENV = "INVENTORY_DEVICE_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("network_device", lambda up, cache: NetworkDeviceService(NetworkDeviceClient(up), cache))
    registry.register("chassis", lambda up, cache: ChassisService(ChassisClient(up), cache))
    registry.register("line_card", lambda up, cache: LineCardService(LineCardClient(up), cache))
    registry.register("power_supply", lambda up, cache: PowerSupplyService(PowerSupplyClient(up), cache))
    registry.register("fan_tray", lambda up, cache: FanTrayService(FanTrayClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Network Device", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(network_device_router)
    app.include_router(chassis_router)
    app.include_router(line_card_router)
    app.include_router(power_supply_router)
    app.include_router(fan_tray_router)
    return app


app = create()
