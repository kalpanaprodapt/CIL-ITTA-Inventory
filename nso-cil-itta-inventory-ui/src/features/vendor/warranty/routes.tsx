import type { RouteObject } from 'react-router-dom';
import { WarrantyCreatePage, WarrantyEditPage } from './WarrantyEditPages';
import { WarrantyDetailPage } from './WarrantyDetailPage';
import { WarrantyListPage } from './WarrantyListPage';

export const warrantyRoutes: RouteObject[] = [
  { path: 'warranties', element: <WarrantyListPage /> },
  { path: 'warranties/new', element: <WarrantyCreatePage /> },
  { path: 'warranties/:id', element: <WarrantyDetailPage /> },
  { path: 'warranties/:id/edit', element: <WarrantyEditPage /> },
];
