import type { RouteObject } from 'react-router-dom';
import { DiscoveryProfileCreatePage, DiscoveryProfileEditPage } from './DiscoveryProfileEditPages';
import { DiscoveryProfileDetailPage } from './DiscoveryProfileDetailPage';
import { DiscoveryProfileListPage } from './DiscoveryProfileListPage';

export const discoveryProfileRoutes: RouteObject[] = [
  { path: 'discovery-profiles', element: <DiscoveryProfileListPage /> },
  { path: 'discovery-profiles/new', element: <DiscoveryProfileCreatePage /> },
  { path: 'discovery-profiles/:id', element: <DiscoveryProfileDetailPage /> },
  { path: 'discovery-profiles/:id/edit', element: <DiscoveryProfileEditPage /> },
];
