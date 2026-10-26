"""FastAPI application for the Vendor and Model domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.vendor.vendor.client import VendorClient
from itta_api.domains.vendor.vendor.service import VendorService
from itta_api.domains.vendor.hardware_model.client import HardwareModelClient
from itta_api.domains.vendor.hardware_model.service import HardwareModelService
from itta_api.domains.vendor.sku.client import SkuClient
from itta_api.domains.vendor.sku.service import SkuService
from itta_api.domains.vendor.support_contract.client import SupportContractClient
from itta_api.domains.vendor.support_contract.service import SupportContractService
from itta_api.domains.vendor.warranty.client import WarrantyClient
from itta_api.domains.vendor.warranty.service import WarrantyService
from itta_api.domains.vendor.vendor.router import router as vendor_router
from itta_api.domains.vendor.hardware_model.router import router as hardware_model_router
from itta_api.domains.vendor.sku.router import router as sku_router
from itta_api.domains.vendor.support_contract.router import router as support_contract_router
from itta_api.domains.vendor.warranty.router import router as warranty_router

DOMAIN = "vendor"
UPSTREAM_ENV = "INVENTORY_VENDOR_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("vendor", lambda up, cache: VendorService(VendorClient(up), cache))
    registry.register("hardware_model", lambda up, cache: HardwareModelService(HardwareModelClient(up), cache))
    registry.register("sku", lambda up, cache: SkuService(SkuClient(up), cache))
    registry.register("support_contract", lambda up, cache: SupportContractService(SupportContractClient(up), cache))
    registry.register("warranty", lambda up, cache: WarrantyService(WarrantyClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Vendor and Model", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(vendor_router)
    app.include_router(hardware_model_router)
    app.include_router(sku_router)
    app.include_router(support_contract_router)
    app.include_router(warranty_router)
    return app


app = create()
