"""FastAPI application for the Virtual and Cloud domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.virtual.vnf.client import VnfClient
from itta_api.domains.virtual.vnf.service import VnfService
from itta_api.domains.virtual.hypervisor.client import HypervisorClient
from itta_api.domains.virtual.hypervisor.service import HypervisorService
from itta_api.domains.virtual.virtual_machine.client import VirtualMachineClient
from itta_api.domains.virtual.virtual_machine.service import VirtualMachineService
from itta_api.domains.virtual.cloud_account.client import CloudAccountClient
from itta_api.domains.virtual.cloud_account.service import CloudAccountService
from itta_api.domains.virtual.k8s_cluster.client import K8sClusterClient
from itta_api.domains.virtual.k8s_cluster.service import K8sClusterService
from itta_api.domains.virtual.vnf.router import router as vnf_router
from itta_api.domains.virtual.hypervisor.router import router as hypervisor_router
from itta_api.domains.virtual.virtual_machine.router import router as virtual_machine_router
from itta_api.domains.virtual.cloud_account.router import router as cloud_account_router
from itta_api.domains.virtual.k8s_cluster.router import router as k8s_cluster_router

DOMAIN = "virtual"
UPSTREAM_ENV = "INVENTORY_VIRTUAL_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("vnf", lambda up, cache: VnfService(VnfClient(up), cache))
    registry.register("hypervisor", lambda up, cache: HypervisorService(HypervisorClient(up), cache))
    registry.register("virtual_machine", lambda up, cache: VirtualMachineService(VirtualMachineClient(up), cache))
    registry.register("cloud_account", lambda up, cache: CloudAccountService(CloudAccountClient(up), cache))
    registry.register("k8s_cluster", lambda up, cache: K8sClusterService(K8sClusterClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Virtual and Cloud", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(vnf_router)
    app.include_router(hypervisor_router)
    app.include_router(virtual_machine_router)
    app.include_router(cloud_account_router)
    app.include_router(k8s_cluster_router)
    return app


app = create()
