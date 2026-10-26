import type { RouteObject } from 'react-router-dom';
import { ServiceInstanceCreatePage, ServiceInstanceEditPage } from './ServiceInstanceEditPages';
import { ServiceInstanceDetailPage } from './ServiceInstanceDetailPage';
import { ServiceInstanceListPage } from './ServiceInstanceListPage';

export const serviceInstanceRoutes: RouteObject[] = [
  { path: 'service-instances', element: <ServiceInstanceListPage /> },
  { path: 'service-instances/new', element: <ServiceInstanceCreatePage /> },
  { path: 'service-instances/:id', element: <ServiceInstanceDetailPage /> },
  { path: 'service-instances/:id/edit', element: <ServiceInstanceEditPage /> },
];
