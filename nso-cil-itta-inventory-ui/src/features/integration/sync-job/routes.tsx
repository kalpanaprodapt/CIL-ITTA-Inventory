import type { RouteObject } from 'react-router-dom';
import { SyncJobCreatePage, SyncJobEditPage } from './SyncJobEditPages';
import { SyncJobDetailPage } from './SyncJobDetailPage';
import { SyncJobListPage } from './SyncJobListPage';

export const syncJobRoutes: RouteObject[] = [
  { path: 'sync-jobs', element: <SyncJobListPage /> },
  { path: 'sync-jobs/new', element: <SyncJobCreatePage /> },
  { path: 'sync-jobs/:id', element: <SyncJobDetailPage /> },
  { path: 'sync-jobs/:id/edit', element: <SyncJobEditPage /> },
];
