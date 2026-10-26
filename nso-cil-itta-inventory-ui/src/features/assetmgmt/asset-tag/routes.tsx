import type { RouteObject } from 'react-router-dom';
import { AssetTagCreatePage, AssetTagEditPage } from './AssetTagEditPages';
import { AssetTagDetailPage } from './AssetTagDetailPage';
import { AssetTagListPage } from './AssetTagListPage';

export const assetTagRoutes: RouteObject[] = [
  { path: 'asset-tags', element: <AssetTagListPage /> },
  { path: 'asset-tags/new', element: <AssetTagCreatePage /> },
  { path: 'asset-tags/:id', element: <AssetTagDetailPage /> },
  { path: 'asset-tags/:id/edit', element: <AssetTagEditPage /> },
];
