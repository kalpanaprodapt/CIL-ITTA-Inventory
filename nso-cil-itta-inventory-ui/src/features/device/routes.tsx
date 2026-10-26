import type { RouteObject } from 'react-router-dom';
import { networkDeviceRoutes } from './network-device/routes';
import { chassisRoutes } from './chassis/routes';
import { lineCardRoutes } from './line-card/routes';
import { powerSupplyRoutes } from './power-supply/routes';
import { fanTrayRoutes } from './fan-tray/routes';

export const deviceDomainRoutes: RouteObject[] = [
  ...networkDeviceRoutes,
  ...chassisRoutes,
  ...lineCardRoutes,
  ...powerSupplyRoutes,
  ...fanTrayRoutes,
].map((route) => ({ ...route, path: `device/${route.path}` }));
