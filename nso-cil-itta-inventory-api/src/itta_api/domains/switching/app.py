"""FastAPI application for the L2 and L3 Routing domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.switching.vlan.client import VlanClient
from itta_api.domains.switching.vlan.service import VlanService
from itta_api.domains.switching.vlan_pool.client import VlanPoolClient
from itta_api.domains.switching.vlan_pool.service import VlanPoolService
from itta_api.domains.switching.route_target.client import RouteTargetClient
from itta_api.domains.switching.route_target.service import RouteTargetService
from itta_api.domains.switching.bgp_session.client import BgpSessionClient
from itta_api.domains.switching.bgp_session.service import BgpSessionService
from itta_api.domains.switching.ospf_area.client import OspfAreaClient
from itta_api.domains.switching.ospf_area.service import OspfAreaService
from itta_api.domains.switching.vlan.router import router as vlan_router
from itta_api.domains.switching.vlan_pool.router import router as vlan_pool_router
from itta_api.domains.switching.route_target.router import router as route_target_router
from itta_api.domains.switching.bgp_session.router import router as bgp_session_router
from itta_api.domains.switching.ospf_area.router import router as ospf_area_router

DOMAIN = "switching"
UPSTREAM_ENV = "INVENTORY_SWITCHING_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("vlan", lambda up, cache: VlanService(VlanClient(up), cache))
    registry.register("vlan_pool", lambda up, cache: VlanPoolService(VlanPoolClient(up), cache))
    registry.register("route_target", lambda up, cache: RouteTargetService(RouteTargetClient(up), cache))
    registry.register("bgp_session", lambda up, cache: BgpSessionService(BgpSessionClient(up), cache))
    registry.register("ospf_area", lambda up, cache: OspfAreaService(OspfAreaClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - L2 and L3 Routing", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(vlan_router)
    app.include_router(vlan_pool_router)
    app.include_router(route_target_router)
    app.include_router(bgp_session_router)
    app.include_router(ospf_area_router)
    return app


app = create()
