"""FastAPI application for the Geography domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.geography.country.client import CountryClient
from itta_api.domains.geography.country.service import CountryService
from itta_api.domains.geography.region.client import RegionClient
from itta_api.domains.geography.region.service import RegionService
from itta_api.domains.geography.city.client import CityClient
from itta_api.domains.geography.city.service import CityService
from itta_api.domains.geography.site.client import SiteClient
from itta_api.domains.geography.site.service import SiteService
from itta_api.domains.geography.building.client import BuildingClient
from itta_api.domains.geography.building.service import BuildingService
from itta_api.domains.geography.country.router import router as country_router
from itta_api.domains.geography.region.router import router as region_router
from itta_api.domains.geography.city.router import router as city_router
from itta_api.domains.geography.site.router import router as site_router
from itta_api.domains.geography.building.router import router as building_router

DOMAIN = "geography"
UPSTREAM_ENV = "INVENTORY_GEOGRAPHY_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("country", lambda up, cache: CountryService(CountryClient(up), cache))
    registry.register("region", lambda up, cache: RegionService(RegionClient(up), cache))
    registry.register("city", lambda up, cache: CityService(CityClient(up), cache))
    registry.register("site", lambda up, cache: SiteService(SiteClient(up), cache))
    registry.register("building", lambda up, cache: BuildingService(BuildingClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Geography", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(country_router)
    app.include_router(region_router)
    app.include_router(city_router)
    app.include_router(site_router)
    app.include_router(building_router)
    return app


app = create()
