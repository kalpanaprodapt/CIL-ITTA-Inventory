import type { RouteObject } from 'react-router-dom';
import { softwareImageRoutes } from './software-image/routes';
import { softwareVersionRoutes } from './software-version/routes';
import { patchRoutes } from './patch/routes';
import { licenseKeyRoutes } from './license-key/routes';
import { licensePoolRoutes } from './license-pool/routes';

export const softwareDomainRoutes: RouteObject[] = [
  ...softwareImageRoutes,
  ...softwareVersionRoutes,
  ...patchRoutes,
  ...licenseKeyRoutes,
  ...licensePoolRoutes,
].map((route) => ({ ...route, path: `software/${route.path}` }));
