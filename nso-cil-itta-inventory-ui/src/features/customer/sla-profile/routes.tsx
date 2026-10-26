import type { RouteObject } from 'react-router-dom';
import { SlaProfileCreatePage, SlaProfileEditPage } from './SlaProfileEditPages';
import { SlaProfileDetailPage } from './SlaProfileDetailPage';
import { SlaProfileListPage } from './SlaProfileListPage';

export const slaProfileRoutes: RouteObject[] = [
  { path: 'sla-profiles', element: <SlaProfileListPage /> },
  { path: 'sla-profiles/new', element: <SlaProfileCreatePage /> },
  { path: 'sla-profiles/:id', element: <SlaProfileDetailPage /> },
  { path: 'sla-profiles/:id/edit', element: <SlaProfileEditPage /> },
];
