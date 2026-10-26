import type { RouteObject } from 'react-router-dom';
import { ComplianceExceptionCreatePage, ComplianceExceptionEditPage } from './ComplianceExceptionEditPages';
import { ComplianceExceptionDetailPage } from './ComplianceExceptionDetailPage';
import { ComplianceExceptionListPage } from './ComplianceExceptionListPage';

export const complianceExceptionRoutes: RouteObject[] = [
  { path: 'compliance-exceptions', element: <ComplianceExceptionListPage /> },
  { path: 'compliance-exceptions/new', element: <ComplianceExceptionCreatePage /> },
  { path: 'compliance-exceptions/:id', element: <ComplianceExceptionDetailPage /> },
  { path: 'compliance-exceptions/:id/edit', element: <ComplianceExceptionEditPage /> },
];
