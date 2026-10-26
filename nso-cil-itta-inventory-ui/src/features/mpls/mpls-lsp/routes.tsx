import type { RouteObject } from 'react-router-dom';
import { MplsLspCreatePage, MplsLspEditPage } from './MplsLspEditPages';
import { MplsLspDetailPage } from './MplsLspDetailPage';
import { MplsLspListPage } from './MplsLspListPage';

export const mplsLspRoutes: RouteObject[] = [
  { path: 'mpls-lsps', element: <MplsLspListPage /> },
  { path: 'mpls-lsps/new', element: <MplsLspCreatePage /> },
  { path: 'mpls-lsps/:id', element: <MplsLspDetailPage /> },
  { path: 'mpls-lsps/:id/edit', element: <MplsLspEditPage /> },
];
