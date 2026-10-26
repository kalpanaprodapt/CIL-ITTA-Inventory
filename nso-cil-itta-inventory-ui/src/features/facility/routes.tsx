import type { RouteObject } from 'react-router-dom';
import { floorRoutes } from './floor/routes';
import { roomRoutes } from './room/routes';
import { rackRoutes } from './rack/routes';
import { rackUnitRoutes } from './rack-unit/routes';
import { powerFeedRoutes } from './power-feed/routes';

export const facilityDomainRoutes: RouteObject[] = [
  ...floorRoutes,
  ...roomRoutes,
  ...rackRoutes,
  ...rackUnitRoutes,
  ...powerFeedRoutes,
].map((route) => ({ ...route, path: `facility/${route.path}` }));
