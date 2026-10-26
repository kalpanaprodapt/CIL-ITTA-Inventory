import type { RouteObject } from 'react-router-dom';
import { PatchPanelCreatePage, PatchPanelEditPage } from './PatchPanelEditPages';
import { PatchPanelDetailPage } from './PatchPanelDetailPage';
import { PatchPanelListPage } from './PatchPanelListPage';

export const patchPanelRoutes: RouteObject[] = [
  { path: 'patch-panels', element: <PatchPanelListPage /> },
  { path: 'patch-panels/new', element: <PatchPanelCreatePage /> },
  { path: 'patch-panels/:id', element: <PatchPanelDetailPage /> },
  { path: 'patch-panels/:id/edit', element: <PatchPanelEditPage /> },
];
