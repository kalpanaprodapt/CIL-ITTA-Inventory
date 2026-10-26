import type { RouteObject } from 'react-router-dom';
import { RegionCreatePage, RegionEditPage } from './RegionEditPages';
import { RegionDetailPage } from './RegionDetailPage';
import { RegionListPage } from './RegionListPage';

export const regionRoutes: RouteObject[] = [
  { path: 'regions', element: <RegionListPage /> },
  { path: 'regions/new', element: <RegionCreatePage /> },
  { path: 'regions/:id', element: <RegionDetailPage /> },
  { path: 'regions/:id/edit', element: <RegionEditPage /> },
];
