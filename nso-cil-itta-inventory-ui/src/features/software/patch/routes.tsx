import type { RouteObject } from 'react-router-dom';
import { PatchCreatePage, PatchEditPage } from './PatchEditPages';
import { PatchDetailPage } from './PatchDetailPage';
import { PatchListPage } from './PatchListPage';

export const patchRoutes: RouteObject[] = [
  { path: 'patchs', element: <PatchListPage /> },
  { path: 'patchs/new', element: <PatchCreatePage /> },
  { path: 'patchs/:id', element: <PatchDetailPage /> },
  { path: 'patchs/:id/edit', element: <PatchEditPage /> },
];
