import type { RouteObject } from 'react-router-dom';
import { DiscoveryJobCreatePage, DiscoveryJobEditPage } from './DiscoveryJobEditPages';
import { DiscoveryJobDetailPage } from './DiscoveryJobDetailPage';
import { DiscoveryJobListPage } from './DiscoveryJobListPage';

export const discoveryJobRoutes: RouteObject[] = [
  { path: 'discovery-jobs', element: <DiscoveryJobListPage /> },
  { path: 'discovery-jobs/new', element: <DiscoveryJobCreatePage /> },
  { path: 'discovery-jobs/:id', element: <DiscoveryJobDetailPage /> },
  { path: 'discovery-jobs/:id/edit', element: <DiscoveryJobEditPage /> },
];
