import type { RouteObject } from 'react-router-dom';
import { SkuCreatePage, SkuEditPage } from './SkuEditPages';
import { SkuDetailPage } from './SkuDetailPage';
import { SkuListPage } from './SkuListPage';

export const skuRoutes: RouteObject[] = [
  { path: 'skus', element: <SkuListPage /> },
  { path: 'skus/new', element: <SkuCreatePage /> },
  { path: 'skus/:id', element: <SkuDetailPage /> },
  { path: 'skus/:id/edit', element: <SkuEditPage /> },
];
