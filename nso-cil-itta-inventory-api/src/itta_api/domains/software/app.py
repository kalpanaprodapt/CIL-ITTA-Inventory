"""FastAPI application for the Software and Licensing domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.software.software_image.client import SoftwareImageClient
from itta_api.domains.software.software_image.service import SoftwareImageService
from itta_api.domains.software.software_version.client import SoftwareVersionClient
from itta_api.domains.software.software_version.service import SoftwareVersionService
from itta_api.domains.software.patch.client import PatchClient
from itta_api.domains.software.patch.service import PatchService
from itta_api.domains.software.license_key.client import LicenseKeyClient
from itta_api.domains.software.license_key.service import LicenseKeyService
from itta_api.domains.software.license_pool.client import LicensePoolClient
from itta_api.domains.software.license_pool.service import LicensePoolService
from itta_api.domains.software.software_image.router import router as software_image_router
from itta_api.domains.software.software_version.router import router as software_version_router
from itta_api.domains.software.patch.router import router as patch_router
from itta_api.domains.software.license_key.router import router as license_key_router
from itta_api.domains.software.license_pool.router import router as license_pool_router

DOMAIN = "software"
UPSTREAM_ENV = "INVENTORY_SOFTWARE_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("software_image", lambda up, cache: SoftwareImageService(SoftwareImageClient(up), cache))
    registry.register("software_version", lambda up, cache: SoftwareVersionService(SoftwareVersionClient(up), cache))
    registry.register("patch", lambda up, cache: PatchService(PatchClient(up), cache))
    registry.register("license_key", lambda up, cache: LicenseKeyService(LicenseKeyClient(up), cache))
    registry.register("license_pool", lambda up, cache: LicensePoolService(LicensePoolClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Software and Licensing", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(software_image_router)
    app.include_router(software_version_router)
    app.include_router(patch_router)
    app.include_router(license_key_router)
    app.include_router(license_pool_router)
    return app


app = create()
