import type { RouteObject } from 'react-router-dom';
import { AssetCreatePage, AssetEditPage } from './AssetEditPages';
import { AssetDetailPage } from './AssetDetailPage';
import { AssetListPage } from './AssetListPage';

export const assetRoutes: RouteObject[] = [
  { path: 'assets', element: <AssetListPage /> },
  { path: 'assets/new', element: <AssetCreatePage /> },
  { path: 'assets/:id', element: <AssetDetailPage /> },
  { path: 'assets/:id/edit', element: <AssetEditPage /> },
];
