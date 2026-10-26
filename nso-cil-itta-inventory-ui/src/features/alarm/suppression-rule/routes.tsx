import type { RouteObject } from 'react-router-dom';
import { SuppressionRuleCreatePage, SuppressionRuleEditPage } from './SuppressionRuleEditPages';
import { SuppressionRuleDetailPage } from './SuppressionRuleDetailPage';
import { SuppressionRuleListPage } from './SuppressionRuleListPage';

export const suppressionRuleRoutes: RouteObject[] = [
  { path: 'suppression-rules', element: <SuppressionRuleListPage /> },
  { path: 'suppression-rules/new', element: <SuppressionRuleCreatePage /> },
  { path: 'suppression-rules/:id', element: <SuppressionRuleDetailPage /> },
  { path: 'suppression-rules/:id/edit', element: <SuppressionRuleEditPage /> },
];
