import type { RouteObject } from 'react-router-dom';
import { opticalChannelRoutes } from './optical-channel/routes';
import { wdmSystemRoutes } from './wdm-system/routes';
import { otnLinkRoutes } from './otn-link/routes';
import { amplifierRoutes } from './amplifier/routes';
import { roadmRoutes } from './roadm/routes';

export const opticalDomainRoutes: RouteObject[] = [
  ...opticalChannelRoutes,
  ...wdmSystemRoutes,
  ...otnLinkRoutes,
  ...amplifierRoutes,
  ...roadmRoutes,
].map((route) => ({ ...route, path: `optical/${route.path}` }));
