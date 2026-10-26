import type { RouteObject } from 'react-router-dom';
import { serviceCatalogItemRoutes } from './service-catalog-item/routes';
import { serviceInstanceRoutes } from './service-instance/routes';
import { serviceOrderRoutes } from './service-order/routes';
import { serviceDependencyRoutes } from './service-dependency/routes';
import { serviceEndpointRoutes } from './service-endpoint/routes';

export const serviceDomainRoutes: RouteObject[] = [
  ...serviceCatalogItemRoutes,
  ...serviceInstanceRoutes,
  ...serviceOrderRoutes,
  ...serviceDependencyRoutes,
  ...serviceEndpointRoutes,
].map((route) => ({ ...route, path: `service/${route.path}` }));
