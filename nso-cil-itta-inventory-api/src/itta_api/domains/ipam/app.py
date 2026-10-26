"""FastAPI application for the IP Management domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.ipam.ip_subnet.client import IpSubnetClient
from itta_api.domains.ipam.ip_subnet.service import IpSubnetService
from itta_api.domains.ipam.ip_address.client import IpAddressClient
from itta_api.domains.ipam.ip_address.service import IpAddressService
from itta_api.domains.ipam.ip_pool.client import IpPoolClient
from itta_api.domains.ipam.ip_pool.service import IpPoolService
from itta_api.domains.ipam.vrf_instance.client import VrfInstanceClient
from itta_api.domains.ipam.vrf_instance.service import VrfInstanceService
from itta_api.domains.ipam.dns_record.client import DnsRecordClient
from itta_api.domains.ipam.dns_record.service import DnsRecordService
from itta_api.domains.ipam.ip_subnet.router import router as ip_subnet_router
from itta_api.domains.ipam.ip_address.router import router as ip_address_router
from itta_api.domains.ipam.ip_pool.router import router as ip_pool_router
from itta_api.domains.ipam.vrf_instance.router import router as vrf_instance_router
from itta_api.domains.ipam.dns_record.router import router as dns_record_router

DOMAIN = "ipam"
UPSTREAM_ENV = "INVENTORY_IPAM_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("ip_subnet", lambda up, cache: IpSubnetService(IpSubnetClient(up), cache))
    registry.register("ip_address", lambda up, cache: IpAddressService(IpAddressClient(up), cache))
    registry.register("ip_pool", lambda up, cache: IpPoolService(IpPoolClient(up), cache))
    registry.register("vrf_instance", lambda up, cache: VrfInstanceService(VrfInstanceClient(up), cache))
    registry.register("dns_record", lambda up, cache: DnsRecordService(DnsRecordClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - IP Management", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(ip_subnet_router)
    app.include_router(ip_address_router)
    app.include_router(ip_pool_router)
    app.include_router(vrf_instance_router)
    app.include_router(dns_record_router)
    return app


app = create()
