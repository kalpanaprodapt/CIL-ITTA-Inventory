import type { RouteObject } from 'react-router-dom';
import { vendorRoutes } from './vendor/routes';
import { hardwareModelRoutes } from './hardware-model/routes';
import { skuRoutes } from './sku/routes';
import { supportContractRoutes } from './support-contract/routes';
import { warrantyRoutes } from './warranty/routes';

export const vendorDomainRoutes: RouteObject[] = [
  ...vendorRoutes,
  ...hardwareModelRoutes,
  ...skuRoutes,
  ...supportContractRoutes,
  ...warrantyRoutes,
].map((route) => ({ ...route, path: `vendor/${route.path}` }));
