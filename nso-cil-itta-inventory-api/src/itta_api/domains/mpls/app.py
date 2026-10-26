"""FastAPI application for the MPLS and VPN domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.mpls.mpls_lsp.client import MplsLspClient
from itta_api.domains.mpls.mpls_lsp.service import MplsLspService
from itta_api.domains.mpls.vpn_service.client import VpnServiceClient
from itta_api.domains.mpls.vpn_service.service import VpnServiceService
from itta_api.domains.mpls.pseudo_wire.client import PseudoWireClient
from itta_api.domains.mpls.pseudo_wire.service import PseudoWireService
from itta_api.domains.mpls.tunnel_endpoint.client import TunnelEndpointClient
from itta_api.domains.mpls.tunnel_endpoint.service import TunnelEndpointService
from itta_api.domains.mpls.qos_policy.client import QosPolicyClient
from itta_api.domains.mpls.qos_policy.service import QosPolicyService
from itta_api.domains.mpls.mpls_lsp.router import router as mpls_lsp_router
from itta_api.domains.mpls.vpn_service.router import router as vpn_service_router
from itta_api.domains.mpls.pseudo_wire.router import router as pseudo_wire_router
from itta_api.domains.mpls.tunnel_endpoint.router import router as tunnel_endpoint_router
from itta_api.domains.mpls.qos_policy.router import router as qos_policy_router

DOMAIN = "mpls"
UPSTREAM_ENV = "INVENTORY_MPLS_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("mpls_lsp", lambda up, cache: MplsLspService(MplsLspClient(up), cache))
    registry.register("vpn_service", lambda up, cache: VpnServiceService(VpnServiceClient(up), cache))
    registry.register("pseudo_wire", lambda up, cache: PseudoWireService(PseudoWireClient(up), cache))
    registry.register("tunnel_endpoint", lambda up, cache: TunnelEndpointService(TunnelEndpointClient(up), cache))
    registry.register("qos_policy", lambda up, cache: QosPolicyService(QosPolicyClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - MPLS and VPN", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(mpls_lsp_router)
    app.include_router(vpn_service_router)
    app.include_router(pseudo_wire_router)
    app.include_router(tunnel_endpoint_router)
    app.include_router(qos_policy_router)
    return app


app = create()
