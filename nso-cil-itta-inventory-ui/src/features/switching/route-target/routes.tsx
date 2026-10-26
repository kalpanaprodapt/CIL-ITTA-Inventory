import type { RouteObject } from 'react-router-dom';
import { RouteTargetCreatePage, RouteTargetEditPage } from './RouteTargetEditPages';
import { RouteTargetDetailPage } from './RouteTargetDetailPage';
import { RouteTargetListPage } from './RouteTargetListPage';

export const routeTargetRoutes: RouteObject[] = [
  { path: 'route-targets', element: <RouteTargetListPage /> },
  { path: 'route-targets/new', element: <RouteTargetCreatePage /> },
  { path: 'route-targets/:id', element: <RouteTargetDetailPage /> },
  { path: 'route-targets/:id/edit', element: <RouteTargetEditPage /> },
];
