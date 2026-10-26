import type { RouteObject } from 'react-router-dom';
import { customerRoutes } from './customer/routes';
import { customerAccountRoutes } from './customer-account/routes';
import { customerContactRoutes } from './customer-contact/routes';
import { contractRoutes } from './contract/routes';
import { slaProfileRoutes } from './sla-profile/routes';

export const customerDomainRoutes: RouteObject[] = [
  ...customerRoutes,
  ...customerAccountRoutes,
  ...customerContactRoutes,
  ...contractRoutes,
  ...slaProfileRoutes,
].map((route) => ({ ...route, path: `customer/${route.path}` }));
