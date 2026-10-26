import type { RouteObject } from 'react-router-dom';
import { PolicyDocumentCreatePage, PolicyDocumentEditPage } from './PolicyDocumentEditPages';
import { PolicyDocumentDetailPage } from './PolicyDocumentDetailPage';
import { PolicyDocumentListPage } from './PolicyDocumentListPage';

export const policyDocumentRoutes: RouteObject[] = [
  { path: 'policy-documents', element: <PolicyDocumentListPage /> },
  { path: 'policy-documents/new', element: <PolicyDocumentCreatePage /> },
  { path: 'policy-documents/:id', element: <PolicyDocumentDetailPage /> },
  { path: 'policy-documents/:id/edit', element: <PolicyDocumentEditPage /> },
];
