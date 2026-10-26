import type { RouteObject } from 'react-router-dom';
import { LagGroupCreatePage, LagGroupEditPage } from './LagGroupEditPages';
import { LagGroupDetailPage } from './LagGroupDetailPage';
import { LagGroupListPage } from './LagGroupListPage';

export const lagGroupRoutes: RouteObject[] = [
  { path: 'lag-groups', element: <LagGroupListPage /> },
  { path: 'lag-groups/new', element: <LagGroupCreatePage /> },
  { path: 'lag-groups/:id', element: <LagGroupDetailPage /> },
  { path: 'lag-groups/:id/edit', element: <LagGroupEditPage /> },
];
