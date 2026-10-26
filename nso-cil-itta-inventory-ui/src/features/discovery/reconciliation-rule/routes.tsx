import type { RouteObject } from 'react-router-dom';
import { ReconciliationRuleCreatePage, ReconciliationRuleEditPage } from './ReconciliationRuleEditPages';
import { ReconciliationRuleDetailPage } from './ReconciliationRuleDetailPage';
import { ReconciliationRuleListPage } from './ReconciliationRuleListPage';

export const reconciliationRuleRoutes: RouteObject[] = [
  { path: 'reconciliation-rules', element: <ReconciliationRuleListPage /> },
  { path: 'reconciliation-rules/new', element: <ReconciliationRuleCreatePage /> },
  { path: 'reconciliation-rules/:id', element: <ReconciliationRuleDetailPage /> },
  { path: 'reconciliation-rules/:id/edit', element: <ReconciliationRuleEditPage /> },
];
