"""FastAPI application for the Customers and SLA domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.customer.customer.client import CustomerClient
from itta_api.domains.customer.customer.service import CustomerService
from itta_api.domains.customer.customer_account.client import CustomerAccountClient
from itta_api.domains.customer.customer_account.service import CustomerAccountService
from itta_api.domains.customer.customer_contact.client import CustomerContactClient
from itta_api.domains.customer.customer_contact.service import CustomerContactService
from itta_api.domains.customer.contract.client import ContractClient
from itta_api.domains.customer.contract.service import ContractService
from itta_api.domains.customer.sla_profile.client import SlaProfileClient
from itta_api.domains.customer.sla_profile.service import SlaProfileService
from itta_api.domains.customer.customer.router import router as customer_router
from itta_api.domains.customer.customer_account.router import router as customer_account_router
from itta_api.domains.customer.customer_contact.router import router as customer_contact_router
from itta_api.domains.customer.contract.router import router as contract_router
from itta_api.domains.customer.sla_profile.router import router as sla_profile_router

DOMAIN = "customer"
UPSTREAM_ENV = "INVENTORY_CUSTOMER_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("customer", lambda up, cache: CustomerService(CustomerClient(up), cache))
    registry.register("customer_account", lambda up, cache: CustomerAccountService(CustomerAccountClient(up), cache))
    registry.register("customer_contact", lambda up, cache: CustomerContactService(CustomerContactClient(up), cache))
    registry.register("contract", lambda up, cache: ContractService(ContractClient(up), cache))
    registry.register("sla_profile", lambda up, cache: SlaProfileService(SlaProfileClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Customers and SLA", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(customer_router)
    app.include_router(customer_account_router)
    app.include_router(customer_contact_router)
    app.include_router(contract_router)
    app.include_router(sla_profile_router)
    return app


app = create()
