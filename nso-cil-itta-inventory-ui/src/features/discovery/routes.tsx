import type { RouteObject } from 'react-router-dom';
import { discoveryJobRoutes } from './discovery-job/routes';
import { discoveryProfileRoutes } from './discovery-profile/routes';
import { discoveredAssetRoutes } from './discovered-asset/routes';
import { reconciliationRuleRoutes } from './reconciliation-rule/routes';
import { reconciliationResultRoutes } from './reconciliation-result/routes';

export const discoveryDomainRoutes: RouteObject[] = [
  ...discoveryJobRoutes,
  ...discoveryProfileRoutes,
  ...discoveredAssetRoutes,
  ...reconciliationRuleRoutes,
  ...reconciliationResultRoutes,
].map((route) => ({ ...route, path: `discovery/${route.path}` }));
