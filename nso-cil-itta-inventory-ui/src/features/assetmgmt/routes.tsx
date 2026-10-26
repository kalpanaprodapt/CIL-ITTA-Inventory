import type { RouteObject } from 'react-router-dom';
import { assetRoutes } from './asset/routes';
import { assetTagRoutes } from './asset-tag/routes';
import { purchaseOrderRoutes } from './purchase-order/routes';
import { invoiceRoutes } from './invoice/routes';
import { depreciationScheduleRoutes } from './depreciation-schedule/routes';

export const assetmgmtDomainRoutes: RouteObject[] = [
  ...assetRoutes,
  ...assetTagRoutes,
  ...purchaseOrderRoutes,
  ...invoiceRoutes,
  ...depreciationScheduleRoutes,
].map((route) => ({ ...route, path: `assetmgmt/${route.path}` }));
