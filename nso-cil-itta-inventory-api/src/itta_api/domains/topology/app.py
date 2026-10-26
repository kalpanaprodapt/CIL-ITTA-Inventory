"""FastAPI application for the Topology and Links domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.topology.link.client import LinkClient
from itta_api.domains.topology.link.service import LinkService
from itta_api.domains.topology.topology_node.client import TopologyNodeClient
from itta_api.domains.topology.topology_node.service import TopologyNodeService
from itta_api.domains.topology.topology_edge.client import TopologyEdgeClient
from itta_api.domains.topology.topology_edge.service import TopologyEdgeService
from itta_api.domains.topology.topology_view.client import TopologyViewClient
from itta_api.domains.topology.topology_view.service import TopologyViewService
from itta_api.domains.topology.path_trace.client import PathTraceClient
from itta_api.domains.topology.path_trace.service import PathTraceService
from itta_api.domains.topology.link.router import router as link_router
from itta_api.domains.topology.topology_node.router import router as topology_node_router
from itta_api.domains.topology.topology_edge.router import router as topology_edge_router
from itta_api.domains.topology.topology_view.router import router as topology_view_router
from itta_api.domains.topology.path_trace.router import router as path_trace_router

DOMAIN = "topology"
UPSTREAM_ENV = "INVENTORY_TOPOLOGY_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("link", lambda up, cache: LinkService(LinkClient(up), cache))
    registry.register("topology_node", lambda up, cache: TopologyNodeService(TopologyNodeClient(up), cache))
    registry.register("topology_edge", lambda up, cache: TopologyEdgeService(TopologyEdgeClient(up), cache))
    registry.register("topology_view", lambda up, cache: TopologyViewService(TopologyViewClient(up), cache))
    registry.register("path_trace", lambda up, cache: PathTraceService(PathTraceClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Topology and Links", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(link_router)
    app.include_router(topology_node_router)
    app.include_router(topology_edge_router)
    app.include_router(topology_view_router)
    app.include_router(path_trace_router)
    return app


app = create()
