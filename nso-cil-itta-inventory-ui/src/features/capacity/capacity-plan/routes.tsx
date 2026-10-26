import type { RouteObject } from 'react-router-dom';
import { CapacityPlanCreatePage, CapacityPlanEditPage } from './CapacityPlanEditPages';
import { CapacityPlanDetailPage } from './CapacityPlanDetailPage';
import { CapacityPlanListPage } from './CapacityPlanListPage';

export const capacityPlanRoutes: RouteObject[] = [
  { path: 'capacity-plans', element: <CapacityPlanListPage /> },
  { path: 'capacity-plans/new', element: <CapacityPlanCreatePage /> },
  { path: 'capacity-plans/:id', element: <CapacityPlanDetailPage /> },
  { path: 'capacity-plans/:id/edit', element: <CapacityPlanEditPage /> },
];
