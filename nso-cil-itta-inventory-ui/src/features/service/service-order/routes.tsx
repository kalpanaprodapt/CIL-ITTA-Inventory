import type { RouteObject } from 'react-router-dom';
import { ServiceOrderCreatePage, ServiceOrderEditPage } from './ServiceOrderEditPages';
import { ServiceOrderDetailPage } from './ServiceOrderDetailPage';
import { ServiceOrderListPage } from './ServiceOrderListPage';

export const serviceOrderRoutes: RouteObject[] = [
  { path: 'service-orders', element: <ServiceOrderListPage /> },
  { path: 'service-orders/new', element: <ServiceOrderCreatePage /> },
  { path: 'service-orders/:id', element: <ServiceOrderDetailPage /> },
  { path: 'service-orders/:id/edit', element: <ServiceOrderEditPage /> },
];
