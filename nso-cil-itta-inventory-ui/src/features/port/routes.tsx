import type { RouteObject } from 'react-router-dom';
import { portRoutes } from './port/routes';
import { interfaceRoutes } from './interface/routes';
import { subInterfaceRoutes } from './sub-interface/routes';
import { lagGroupRoutes } from './lag-group/routes';
import { transceiverRoutes } from './transceiver/routes';

export const portDomainRoutes: RouteObject[] = [
  ...portRoutes,
  ...interfaceRoutes,
  ...subInterfaceRoutes,
  ...lagGroupRoutes,
  ...transceiverRoutes,
].map((route) => ({ ...route, path: `port/${route.path}` }));
