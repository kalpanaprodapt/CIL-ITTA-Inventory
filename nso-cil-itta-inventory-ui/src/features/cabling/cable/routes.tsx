import type { RouteObject } from 'react-router-dom';
import { CableCreatePage, CableEditPage } from './CableEditPages';
import { CableDetailPage } from './CableDetailPage';
import { CableListPage } from './CableListPage';

export const cableRoutes: RouteObject[] = [
  { path: 'cables', element: <CableListPage /> },
  { path: 'cables/new', element: <CableCreatePage /> },
  { path: 'cables/:id', element: <CableDetailPage /> },
  { path: 'cables/:id/edit', element: <CableEditPage /> },
];
