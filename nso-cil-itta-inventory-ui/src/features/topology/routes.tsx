import type { RouteObject } from 'react-router-dom';
import { linkRoutes } from './link/routes';
import { topologyNodeRoutes } from './topology-node/routes';
import { topologyEdgeRoutes } from './topology-edge/routes';
import { topologyViewRoutes } from './topology-view/routes';
import { pathTraceRoutes } from './path-trace/routes';

export const topologyDomainRoutes: RouteObject[] = [
  ...linkRoutes,
  ...topologyNodeRoutes,
  ...topologyEdgeRoutes,
  ...topologyViewRoutes,
  ...pathTraceRoutes,
].map((route) => ({ ...route, path: `topology/${route.path}` }));
