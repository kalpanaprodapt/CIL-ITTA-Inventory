import type { RouteObject } from 'react-router-dom';
import { WorkOrderCreatePage, WorkOrderEditPage } from './WorkOrderEditPages';
import { WorkOrderDetailPage } from './WorkOrderDetailPage';
import { WorkOrderListPage } from './WorkOrderListPage';

export const workOrderRoutes: RouteObject[] = [
  { path: 'work-orders', element: <WorkOrderListPage /> },
  { path: 'work-orders/new', element: <WorkOrderCreatePage /> },
  { path: 'work-orders/:id', element: <WorkOrderDetailPage /> },
  { path: 'work-orders/:id/edit', element: <WorkOrderEditPage /> },
];
