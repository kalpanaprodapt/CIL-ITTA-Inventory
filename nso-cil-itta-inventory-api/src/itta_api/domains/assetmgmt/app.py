"""FastAPI application for the Asset and Finance domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.assetmgmt.asset.client import AssetClient
from itta_api.domains.assetmgmt.asset.service import AssetService
from itta_api.domains.assetmgmt.asset_tag.client import AssetTagClient
from itta_api.domains.assetmgmt.asset_tag.service import AssetTagService
from itta_api.domains.assetmgmt.purchase_order.client import PurchaseOrderClient
from itta_api.domains.assetmgmt.purchase_order.service import PurchaseOrderService
from itta_api.domains.assetmgmt.invoice.client import InvoiceClient
from itta_api.domains.assetmgmt.invoice.service import InvoiceService
from itta_api.domains.assetmgmt.depreciation_schedule.client import DepreciationScheduleClient
from itta_api.domains.assetmgmt.depreciation_schedule.service import DepreciationScheduleService
from itta_api.domains.assetmgmt.asset.router import router as asset_router
from itta_api.domains.assetmgmt.asset_tag.router import router as asset_tag_router
from itta_api.domains.assetmgmt.purchase_order.router import router as purchase_order_router
from itta_api.domains.assetmgmt.invoice.router import router as invoice_router
from itta_api.domains.assetmgmt.depreciation_schedule.router import router as depreciation_schedule_router

DOMAIN = "assetmgmt"
UPSTREAM_ENV = "INVENTORY_ASSETMGMT_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("asset", lambda up, cache: AssetService(AssetClient(up), cache))
    registry.register("asset_tag", lambda up, cache: AssetTagService(AssetTagClient(up), cache))
    registry.register("purchase_order", lambda up, cache: PurchaseOrderService(PurchaseOrderClient(up), cache))
    registry.register("invoice", lambda up, cache: InvoiceService(InvoiceClient(up), cache))
    registry.register("depreciation_schedule", lambda up, cache: DepreciationScheduleService(DepreciationScheduleClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Asset and Finance", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(asset_router)
    app.include_router(asset_tag_router)
    app.include_router(purchase_order_router)
    app.include_router(invoice_router)
    app.include_router(depreciation_schedule_router)
    return app


app = create()
