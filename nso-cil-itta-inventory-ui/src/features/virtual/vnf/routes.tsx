import type { RouteObject } from 'react-router-dom';
import { VnfCreatePage, VnfEditPage } from './VnfEditPages';
import { VnfDetailPage } from './VnfDetailPage';
import { VnfListPage } from './VnfListPage';

export const vnfRoutes: RouteObject[] = [
  { path: 'vnfs', element: <VnfListPage /> },
  { path: 'vnfs/new', element: <VnfCreatePage /> },
  { path: 'vnfs/:id', element: <VnfDetailPage /> },
  { path: 'vnfs/:id/edit', element: <VnfEditPage /> },
];
