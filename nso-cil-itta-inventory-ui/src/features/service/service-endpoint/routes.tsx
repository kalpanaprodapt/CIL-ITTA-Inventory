import type { RouteObject } from 'react-router-dom';
import { ServiceEndpointCreatePage, ServiceEndpointEditPage } from './ServiceEndpointEditPages';
import { ServiceEndpointDetailPage } from './ServiceEndpointDetailPage';
import { ServiceEndpointListPage } from './ServiceEndpointListPage';

export const serviceEndpointRoutes: RouteObject[] = [
  { path: 'service-endpoints', element: <ServiceEndpointListPage /> },
  { path: 'service-endpoints/new', element: <ServiceEndpointCreatePage /> },
  { path: 'service-endpoints/:id', element: <ServiceEndpointDetailPage /> },
  { path: 'service-endpoints/:id/edit', element: <ServiceEndpointEditPage /> },
];
