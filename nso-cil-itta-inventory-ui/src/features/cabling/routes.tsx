import type { RouteObject } from 'react-router-dom';
import { cableRoutes } from './cable/routes';
import { patchPanelRoutes } from './patch-panel/routes';
import { patchPortRoutes } from './patch-port/routes';
import { spliceRoutes } from './splice/routes';
import { fiberStrandRoutes } from './fiber-strand/routes';

export const cablingDomainRoutes: RouteObject[] = [
  ...cableRoutes,
  ...patchPanelRoutes,
  ...patchPortRoutes,
  ...spliceRoutes,
  ...fiberStrandRoutes,
].map((route) => ({ ...route, path: `cabling/${route.path}` }));
