import type { RouteObject } from 'react-router-dom';
import { PatchPortCreatePage, PatchPortEditPage } from './PatchPortEditPages';
import { PatchPortDetailPage } from './PatchPortDetailPage';
import { PatchPortListPage } from './PatchPortListPage';

export const patchPortRoutes: RouteObject[] = [
  { path: 'patch-ports', element: <PatchPortListPage /> },
  { path: 'patch-ports/new', element: <PatchPortCreatePage /> },
  { path: 'patch-ports/:id', element: <PatchPortDetailPage /> },
  { path: 'patch-ports/:id/edit', element: <PatchPortEditPage /> },
];
