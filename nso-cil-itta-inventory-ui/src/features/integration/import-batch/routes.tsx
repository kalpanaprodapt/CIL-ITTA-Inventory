import type { RouteObject } from 'react-router-dom';
import { ImportBatchCreatePage, ImportBatchEditPage } from './ImportBatchEditPages';
import { ImportBatchDetailPage } from './ImportBatchDetailPage';
import { ImportBatchListPage } from './ImportBatchListPage';

export const importBatchRoutes: RouteObject[] = [
  { path: 'import-batchs', element: <ImportBatchListPage /> },
  { path: 'import-batchs/new', element: <ImportBatchCreatePage /> },
  { path: 'import-batchs/:id', element: <ImportBatchDetailPage /> },
  { path: 'import-batchs/:id/edit', element: <ImportBatchEditPage /> },
];
