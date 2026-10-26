import type { RouteObject } from 'react-router-dom';
import { ServiceDependencyCreatePage, ServiceDependencyEditPage } from './ServiceDependencyEditPages';
import { ServiceDependencyDetailPage } from './ServiceDependencyDetailPage';
import { ServiceDependencyListPage } from './ServiceDependencyListPage';

export const serviceDependencyRoutes: RouteObject[] = [
  { path: 'service-dependencies', element: <ServiceDependencyListPage /> },
  { path: 'service-dependencies/new', element: <ServiceDependencyCreatePage /> },
  { path: 'service-dependencies/:id', element: <ServiceDependencyDetailPage /> },
  { path: 'service-dependencies/:id/edit', element: <ServiceDependencyEditPage /> },
];
