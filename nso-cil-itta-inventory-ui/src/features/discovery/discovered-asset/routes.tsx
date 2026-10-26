import type { RouteObject } from 'react-router-dom';
import { DiscoveredAssetCreatePage, DiscoveredAssetEditPage } from './DiscoveredAssetEditPages';
import { DiscoveredAssetDetailPage } from './DiscoveredAssetDetailPage';
import { DiscoveredAssetListPage } from './DiscoveredAssetListPage';

export const discoveredAssetRoutes: RouteObject[] = [
  { path: 'discovered-assets', element: <DiscoveredAssetListPage /> },
  { path: 'discovered-assets/new', element: <DiscoveredAssetCreatePage /> },
  { path: 'discovered-assets/:id', element: <DiscoveredAssetDetailPage /> },
  { path: 'discovered-assets/:id/edit', element: <DiscoveredAssetEditPage /> },
];
