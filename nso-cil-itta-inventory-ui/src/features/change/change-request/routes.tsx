import type { RouteObject } from 'react-router-dom';
import { ChangeRequestCreatePage, ChangeRequestEditPage } from './ChangeRequestEditPages';
import { ChangeRequestDetailPage } from './ChangeRequestDetailPage';
import { ChangeRequestListPage } from './ChangeRequestListPage';

export const changeRequestRoutes: RouteObject[] = [
  { path: 'change-requests', element: <ChangeRequestListPage /> },
  { path: 'change-requests/new', element: <ChangeRequestCreatePage /> },
  { path: 'change-requests/:id', element: <ChangeRequestDetailPage /> },
  { path: 'change-requests/:id/edit', element: <ChangeRequestEditPage /> },
];
