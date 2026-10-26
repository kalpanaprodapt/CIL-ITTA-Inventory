import type { RouteObject } from 'react-router-dom';
import { ComplianceRuleCreatePage, ComplianceRuleEditPage } from './ComplianceRuleEditPages';
import { ComplianceRuleDetailPage } from './ComplianceRuleDetailPage';
import { ComplianceRuleListPage } from './ComplianceRuleListPage';

export const complianceRuleRoutes: RouteObject[] = [
  { path: 'compliance-rules', element: <ComplianceRuleListPage /> },
  { path: 'compliance-rules/new', element: <ComplianceRuleCreatePage /> },
  { path: 'compliance-rules/:id', element: <ComplianceRuleDetailPage /> },
  { path: 'compliance-rules/:id/edit', element: <ComplianceRuleEditPage /> },
];
