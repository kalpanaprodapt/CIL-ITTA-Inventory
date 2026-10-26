import type { RouteObject } from 'react-router-dom';
import { radioUnitRoutes } from './radio-unit/routes';
import { baseStationRoutes } from './base-station/routes';
import { antennaRoutes } from './antenna/routes';
import { sectorRoutes } from './sector/routes';
import { backhaulLinkRoutes } from './backhaul-link/routes';

export const radioDomainRoutes: RouteObject[] = [
  ...radioUnitRoutes,
  ...baseStationRoutes,
  ...antennaRoutes,
  ...sectorRoutes,
  ...backhaulLinkRoutes,
].map((route) => ({ ...route, path: `radio/${route.path}` }));
