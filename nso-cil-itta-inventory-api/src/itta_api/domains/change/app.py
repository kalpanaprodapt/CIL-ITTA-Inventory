"""FastAPI application for the Change and Work Management domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.change.change_request.client import ChangeRequestClient
from itta_api.domains.change.change_request.service import ChangeRequestService
from itta_api.domains.change.maintenance_window.client import MaintenanceWindowClient
from itta_api.domains.change.maintenance_window.service import MaintenanceWindowService
from itta_api.domains.change.work_order.client import WorkOrderClient
from itta_api.domains.change.work_order.service import WorkOrderService
from itta_api.domains.change.field_task.client import FieldTaskClient
from itta_api.domains.change.field_task.service import FieldTaskService
from itta_api.domains.change.technician.client import TechnicianClient
from itta_api.domains.change.technician.service import TechnicianService
from itta_api.domains.change.change_request.router import router as change_request_router
from itta_api.domains.change.maintenance_window.router import router as maintenance_window_router
from itta_api.domains.change.work_order.router import router as work_order_router
from itta_api.domains.change.field_task.router import router as field_task_router
from itta_api.domains.change.technician.router import router as technician_router

DOMAIN = "change"
UPSTREAM_ENV = "INVENTORY_CHANGE_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("change_request", lambda up, cache: ChangeRequestService(ChangeRequestClient(up), cache))
    registry.register("maintenance_window", lambda up, cache: MaintenanceWindowService(MaintenanceWindowClient(up), cache))
    registry.register("work_order", lambda up, cache: WorkOrderService(WorkOrderClient(up), cache))
    registry.register("field_task", lambda up, cache: FieldTaskService(FieldTaskClient(up), cache))
    registry.register("technician", lambda up, cache: TechnicianService(TechnicianClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Change and Work Management", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(change_request_router)
    app.include_router(maintenance_window_router)
    app.include_router(work_order_router)
    app.include_router(field_task_router)
    app.include_router(technician_router)
    return app


app = create()
