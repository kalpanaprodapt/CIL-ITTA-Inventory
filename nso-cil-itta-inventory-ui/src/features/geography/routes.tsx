import type { RouteObject } from 'react-router-dom';
import { countryRoutes } from './country/routes';
import { regionRoutes } from './region/routes';
import { cityRoutes } from './city/routes';
import { siteRoutes } from './site/routes';
import { buildingRoutes } from './building/routes';

export const geographyDomainRoutes: RouteObject[] = [
  ...countryRoutes,
  ...regionRoutes,
  ...cityRoutes,
  ...siteRoutes,
  ...buildingRoutes,
].map((route) => ({ ...route, path: `geography/${route.path}` }));
