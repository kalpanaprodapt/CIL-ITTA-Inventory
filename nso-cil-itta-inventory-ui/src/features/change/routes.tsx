import type { RouteObject } from 'react-router-dom';
import { changeRequestRoutes } from './change-request/routes';
import { maintenanceWindowRoutes } from './maintenance-window/routes';
import { workOrderRoutes } from './work-order/routes';
import { fieldTaskRoutes } from './field-task/routes';
import { technicianRoutes } from './technician/routes';

export const changeDomainRoutes: RouteObject[] = [
  ...changeRequestRoutes,
  ...maintenanceWindowRoutes,
  ...workOrderRoutes,
  ...fieldTaskRoutes,
  ...technicianRoutes,
].map((route) => ({ ...route, path: `change/${route.path}` }));
