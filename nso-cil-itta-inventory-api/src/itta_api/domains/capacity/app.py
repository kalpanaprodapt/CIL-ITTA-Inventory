"""FastAPI application for the Capacity Planning domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.capacity.capacity_plan.client import CapacityPlanClient
from itta_api.domains.capacity.capacity_plan.service import CapacityPlanService
from itta_api.domains.capacity.utilization_sample.client import UtilizationSampleClient
from itta_api.domains.capacity.utilization_sample.service import UtilizationSampleService
from itta_api.domains.capacity.threshold_policy.client import ThresholdPolicyClient
from itta_api.domains.capacity.threshold_policy.service import ThresholdPolicyService
from itta_api.domains.capacity.forecast_model.client import ForecastModelClient
from itta_api.domains.capacity.forecast_model.service import ForecastModelService
from itta_api.domains.capacity.reservation.client import ReservationClient
from itta_api.domains.capacity.reservation.service import ReservationService
from itta_api.domains.capacity.capacity_plan.router import router as capacity_plan_router
from itta_api.domains.capacity.utilization_sample.router import router as utilization_sample_router
from itta_api.domains.capacity.threshold_policy.router import router as threshold_policy_router
from itta_api.domains.capacity.forecast_model.router import router as forecast_model_router
from itta_api.domains.capacity.reservation.router import router as reservation_router

DOMAIN = "capacity"
UPSTREAM_ENV = "INVENTORY_CAPACITY_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("capacity_plan", lambda up, cache: CapacityPlanService(CapacityPlanClient(up), cache))
    registry.register("utilization_sample", lambda up, cache: UtilizationSampleService(UtilizationSampleClient(up), cache))
    registry.register("threshold_policy", lambda up, cache: ThresholdPolicyService(ThresholdPolicyClient(up), cache))
    registry.register("forecast_model", lambda up, cache: ForecastModelService(ForecastModelClient(up), cache))
    registry.register("reservation", lambda up, cache: ReservationService(ReservationClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Capacity Planning", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(capacity_plan_router)
    app.include_router(utilization_sample_router)
    app.include_router(threshold_policy_router)
    app.include_router(forecast_model_router)
    app.include_router(reservation_router)
    return app


app = create()
