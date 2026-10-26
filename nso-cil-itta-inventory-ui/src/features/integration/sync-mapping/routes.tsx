import type { RouteObject } from 'react-router-dom';
import { SyncMappingCreatePage, SyncMappingEditPage } from './SyncMappingEditPages';
import { SyncMappingDetailPage } from './SyncMappingDetailPage';
import { SyncMappingListPage } from './SyncMappingListPage';

export const syncMappingRoutes: RouteObject[] = [
  { path: 'sync-mappings', element: <SyncMappingListPage /> },
  { path: 'sync-mappings/new', element: <SyncMappingCreatePage /> },
  { path: 'sync-mappings/:id', element: <SyncMappingDetailPage /> },
  { path: 'sync-mappings/:id/edit', element: <SyncMappingEditPage /> },
];
