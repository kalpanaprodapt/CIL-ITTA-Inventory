import type { RouteObject } from 'react-router-dom';
import { SpliceCreatePage, SpliceEditPage } from './SpliceEditPages';
import { SpliceDetailPage } from './SpliceDetailPage';
import { SpliceListPage } from './SpliceListPage';

export const spliceRoutes: RouteObject[] = [
  { path: 'splices', element: <SpliceListPage /> },
  { path: 'splices/new', element: <SpliceCreatePage /> },
  { path: 'splices/:id', element: <SpliceDetailPage /> },
  { path: 'splices/:id/edit', element: <SpliceEditPage /> },
];
