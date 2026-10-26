import type { RouteObject } from 'react-router-dom';
import { circuitRoutes } from './circuit/routes';
import { circuitSegmentRoutes } from './circuit-segment/routes';
import { circuitProviderRoutes } from './circuit-provider/routes';
import { circuitTerminationRoutes } from './circuit-termination/routes';
import { circuitOrderRoutes } from './circuit-order/routes';

export const circuitDomainRoutes: RouteObject[] = [
  ...circuitRoutes,
  ...circuitSegmentRoutes,
  ...circuitProviderRoutes,
  ...circuitTerminationRoutes,
  ...circuitOrderRoutes,
].map((route) => ({ ...route, path: `circuit/${route.path}` }));
