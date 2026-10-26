import type { RouteObject } from 'react-router-dom';
import { ComplianceCheckCreatePage, ComplianceCheckEditPage } from './ComplianceCheckEditPages';
import { ComplianceCheckDetailPage } from './ComplianceCheckDetailPage';
import { ComplianceCheckListPage } from './ComplianceCheckListPage';

export const complianceCheckRoutes: RouteObject[] = [
  { path: 'compliance-checks', element: <ComplianceCheckListPage /> },
  { path: 'compliance-checks/new', element: <ComplianceCheckCreatePage /> },
  { path: 'compliance-checks/:id', element: <ComplianceCheckDetailPage /> },
  { path: 'compliance-checks/:id/edit', element: <ComplianceCheckEditPage /> },
];
