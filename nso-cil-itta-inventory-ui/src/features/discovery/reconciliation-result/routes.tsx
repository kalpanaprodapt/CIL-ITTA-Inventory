import type { RouteObject } from 'react-router-dom';
import { ReconciliationResultCreatePage, ReconciliationResultEditPage } from './ReconciliationResultEditPages';
import { ReconciliationResultDetailPage } from './ReconciliationResultDetailPage';
import { ReconciliationResultListPage } from './ReconciliationResultListPage';

export const reconciliationResultRoutes: RouteObject[] = [
  { path: 'reconciliation-results', element: <ReconciliationResultListPage /> },
  { path: 'reconciliation-results/new', element: <ReconciliationResultCreatePage /> },
  { path: 'reconciliation-results/:id', element: <ReconciliationResultDetailPage /> },
  { path: 'reconciliation-results/:id/edit', element: <ReconciliationResultEditPage /> },
];
